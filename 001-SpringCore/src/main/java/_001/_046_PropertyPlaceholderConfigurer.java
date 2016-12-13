/*
Often times, most Spring developers just put the entire deployment details (database details, log file path) in XML bean configuration file. But, 
in a corporate environment, deployment detail is usually only can ‘touch’ by your system or database administrator, they just refuse to access your 
bean configuration file directly, and they will request a separate file for deployment configuration, for example, a simple properties, with 
deployment detail only.

To fix it, you can use PropertyPlaceholderConfigurer class to externalize the deployment details into a properties file, and access from bean 
configuration file via a special format – ${variable}.

Declare a PropertyPlaceholderConfigurer in bean configuration file and map to the ‘database.properties‘ properties file you create.
 */

package _001;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

public class _046_PropertyPlaceholderConfigurer {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_046_PropertyPlaceholderConfigurer.xml");
		String userId = "admin";
		LoginManager46 loginManager = (LoginManager46) context.getBean("loginManager46");
		System.out.println("Login userId " + userId);
		User46 user = loginManager.login(userId, false);

		System.out.println("Log-in again");
		loginManager.login(userId, true);

		System.out.println("Login History");
		LoginHistoryDAO46 loginHistoryDao = (LoginHistoryDAO46) context.getBean("loginHistoryDao");
		List<LoginHistory46> loginHistoryList = loginHistoryDao.findLoginHistory(user);
		for (LoginHistory46 loginHistory : loginHistoryList) {
			System.out.println(loginHistory);
		}
		((AbstractApplicationContext)context).registerShutdownHook();
	}
}

@Component
class LoginManager46 implements ApplicationContextAware {
	@Autowired
	private UserDAO46 userDao;
	private ApplicationContext context;

	public User46 login(String userId, boolean importantUser) {
		User46 user = userDao.findUser(userId,importantUser);
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": Publishing events for " + user.getUserId() + "------------");
		context.publishEvent(user);
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": Finished publishing events for " + user.getUserId() + "--------------\n");
		return user;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context=applicationContext;		
	}    
}

@Component
class Listeners46 {
	@Autowired
	private LoginHistoryDAO46 loginHistoryDao;

	@EventListener(condition = "#user1.importantUser")
	public void userListener(User46 user1) {
		System.out.println("++++" + Thread.currentThread().getName()+ ": Entered userListener +++++");
		loginHistoryDao.recordLoginHistory(user1);
		System.out.println("++++" + Thread.currentThread().getName()+ ": Finished processing login event+++++");
	}  
}

class LoginHistoryDAO46 extends JdbcDaoSupport {
    public void recordLoginHistory(User46 user) {
        System.out.println("Add entry to login history for " + user.getUserId());
        getJdbcTemplate().update("insert into user_login_history(status, session_id, login_time, user_id) values(?, ?, ?, ?)",
        		"SUCCESS", user.getSessionId(), user.getLoginTime(), user.getId());
    }
     
    public List<LoginHistory46> findLoginHistory(final User46 user) {
        return getJdbcTemplate().query("select id, status, login_time, session_id from user_login_history where user_id=?", 
                new Object[]{user.getId()}, 
                new RowMapper<LoginHistory46>(){
 
            public LoginHistory46 mapRow(ResultSet rs, int rowNum) throws SQLException {
                LoginHistory46 loginHistory = new LoginHistory46();
                loginHistory.setId(rs.getInt("id"));
                loginHistory.setStatus(rs.getString("status"));
                loginHistory.setLoginTime(rs.getDate("login_time"));
                loginHistory.setSessionId(rs.getLong("session_id"));
                loginHistory.setUser(user);
                return loginHistory;
            }});
    }    
}

class UserDAO46 extends JdbcDaoSupport {
	
	public User46 findUser(String userId, final boolean importantUser) {
		return getJdbcTemplate().query("select id, user_id, password from user_details where user_id=?",
				new Object[] { userId },
				new ResultSetExtractor<User46>() {
			public User46 extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return new User46(rs.getInt(1), rs.getString(2), rs.getString(3), importantUser);
			}
		});
	}
}

class LoginHistory46 {
    private int id;
    private User46 user;
    private Long sessionId;
    private String status;
    private Date loginTime;
    private Date logoutTime;
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public User46 getUser() {
        return user;
    }
 
    public void setUser(User46 user) {
        this.user = user;
    }
 
    public Long getSessionId() {
        return sessionId;
    }
 
    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
 
    public Date getLoginTime() {
        return loginTime;
    }
 
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
 
    public Date getLogoutTime() {
        return logoutTime;
    }
 
    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
     
    public String toString() {
        return "User: " + user.getUserId() + ", sessionId: " + user.getSessionId() + ", loginTime: " + getLoginTime() + ", status: " + getStatus();
    }
 
}

class User46 {
    private Integer id;
    private String userId;
    private String password;
    private Date loginTime;
    private Long sessionId;
    private boolean importantUser;
 
    public User46(Integer id, String userId, String password, boolean importantUser) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.sessionId = UUID.randomUUID().getMostSignificantBits();
        this.loginTime = new Date();
        this.setImportantUser(importantUser);
    }
 
    public Integer getId() {
        return id;
    }
 
    public String getUserId() {
        return userId;
    }
 
    public String getPassword() {
        return password;
    }
 
    public Date getLoginTime() {
        return loginTime;
    }
 
    public Long getSessionId() {
        return sessionId;
    }

	public boolean isImportantUser() {
		return importantUser;
	}

	public void setImportantUser(boolean importantUser) {
		this.importantUser = importantUser;
	}
     
}