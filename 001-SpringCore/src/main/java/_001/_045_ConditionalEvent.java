/*
Conditional event handling:
To make @EventListener even more interesting there is an ability to handle only those events of a given type which fulfill given condition(s) 
written in SpEL.
 */

package _001;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

public class _045_ConditionalEvent {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_045_ConditionalEvent.xml");
		String userId = "admin";
		LoginManager45 loginManager = (LoginManager45) context.getBean("loginManager45");
		System.out.println("Login userId " + userId);
		User45 user = loginManager.login(userId, false);

		System.out.println("Log-in again");
		loginManager.login(userId, true);

		System.out.println("Login History");
		LoginHistoryDAO45 loginHistoryDao = (LoginHistoryDAO45) context.getBean("loginHistoryDao");
		List<LoginHistory45> loginHistoryList = loginHistoryDao.findLoginHistory(user);
		for (LoginHistory45 loginHistory : loginHistoryList) {
			System.out.println(loginHistory);
		}
		((AbstractApplicationContext)context).registerShutdownHook();
	}
}

//@Component
class LoginManager45 implements ApplicationContextAware {
	@Autowired
	private UserDAO45 userDao;
	private ApplicationContext context;

	public User45 login(String userId, boolean importantUser) {
		User45 user = userDao.findUser(userId,importantUser);
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": Publishing events for " + user.getUserId() + "------------");
		context.publishEvent(user);
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": Finished publishing events for " + user.getUserId() + "--------------\n");
		return user;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context=applicationContext;		
	}    
}

//@Component
class Listeners45 {
	@Autowired
	private LoginHistoryDAO45 loginHistoryDao;

	@EventListener(condition = "#user1.importantUser")
	public void userListener(User45 user1) {
		System.out.println("++++" + Thread.currentThread().getName()+ ": Entered userListener +++++");
		loginHistoryDao.recordLoginHistory(user1);
		System.out.println("++++" + Thread.currentThread().getName()+ ": Finished processing login event+++++");
	}  
}

class LoginHistoryDAO45 extends JdbcDaoSupport {
    public void recordLoginHistory(User45 user) {
        System.out.println("Add entry to login history for " + user.getUserId());
        getJdbcTemplate().update("insert into user_login_history(status, session_id, login_time, user_id) values(?, ?, ?, ?)",
        		"SUCCESS", user.getSessionId(), user.getLoginTime(), user.getId());
    }
     
    public List<LoginHistory45> findLoginHistory(final User45 user) {
        return getJdbcTemplate().query("select id, status, login_time, session_id from user_login_history where user_id=?", 
                new Object[]{user.getId()}, 
                new RowMapper<LoginHistory45>(){
 
            public LoginHistory45 mapRow(ResultSet rs, int rowNum) throws SQLException {
                LoginHistory45 loginHistory = new LoginHistory45();
                loginHistory.setId(rs.getInt("id"));
                loginHistory.setStatus(rs.getString("status"));
                loginHistory.setLoginTime(rs.getDate("login_time"));
                loginHistory.setSessionId(rs.getLong("session_id"));
                loginHistory.setUser(user);
                return loginHistory;
            }});
    }    
}

class UserDAO45 extends JdbcDaoSupport {
	
	public User45 findUser(String userId, final boolean importantUser) {
		return getJdbcTemplate().query("select id, user_id, password from user_details where user_id=?",
				new Object[] { userId },
				new ResultSetExtractor<User45>() {
			public User45 extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return new User45(rs.getInt(1), rs.getString(2), rs.getString(3), importantUser);
			}
		});
	}
}

class LoginHistory45 {
    private int id;
    private User45 user;
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
 
    public User45 getUser() {
        return user;
    }
 
    public void setUser(User45 user) {
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

class User45 {
    private Integer id;
    private String userId;
    private String password;
    private Date loginTime;
    private Long sessionId;
    private boolean importantUser;
 
    public User45(Integer id, String userId, String password, boolean importantUser) {
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