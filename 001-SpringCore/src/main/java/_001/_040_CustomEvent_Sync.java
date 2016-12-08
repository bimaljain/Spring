/*
In this article, we will look into an example of spring’s ApplicationEvent, to maintain the login history of users. 
The bean responsible for recording login history will implement ApplicationListener and register itself in the context XML. 
When a user successfully logs in, an ApplicationEvent will be published.  The login history bean registered will receive the event 
and add an entry for the logged in user.

DROP TABLE BJ_USER_DETAILS;
DROP TABLE BJ_USER_LOGIN_HISTORY;
 
CREATE TABLE BJ_USER_DETAILS (
  ID INT NOT NULL,
  USER_ID VARCHAR(15) NOT NULL,
  PASSWORD VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID)
);
 
CREATE TABLE BJ_USER_LOGIN_HISTORY (
  ID INT NOT NULL,
  STATUS VARCHAR(15) NOT NULL,
  SESSION_ID INT DEFAULT NULL,
  LOGIN_TIME TIMESTAMP DEFAULT NULL,
  LOGOUT_TIME TIMESTAMP DEFAULT NULL,
  USER_ID INT DEFAULT NULL,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE BJ_SEQUENCE
MINVALUE 1
START WITH 1
INCREMENT BY 1
CACHE 10

insert into bj_user_details values (1, 'admin', 'admin');
commit;

select * from BJ_USER_DETAILS;
select * from BJ_USER_LOGIN_HISTORY;

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
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class _040_CustomEvent_Sync {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_040_CustomEvent_Sync.xml");
		String userId = "admin";
		String password = "admin";
		LoginManager loginManager = (LoginManager) context.getBean("loginManager");
		System.out.println("Login userId " + userId);
		User user = loginManager.login(userId, password);

		System.out.println("Log-in again");
		loginManager.login(userId, password);

		System.out.println("Login History");
		LoginHistoryDAO loginHistoryDao = (LoginHistoryDAO) context.getBean("loginHistoryDao");
		List<LoginHistory> loginHistoryList = loginHistoryDao.findLoginHistory(user);
		for (LoginHistory loginHistory : loginHistoryList) {
			System.out.println(loginHistory);
		}
	}
}

class LoginManager implements ApplicationContextAware {
    @Autowired
    private UserDAO userDao;
    
    private ApplicationContext applicationContext;
 
    public User login(String userId, String password) {
            User user = userDao.findUser(userId);
            System.out.println("-----------------" +Thread.currentThread().getName()+ ": Publishing successful login event for "  
            				+ user.getUserId() + "------------");
            applicationContext.publishEvent(new LoginEvent(user));
            System.out.println("-----------------" +Thread.currentThread().getName()+ ": Finished publishing login event for " 
            				+ user.getUserId() + "--------------\n");
            return user;
    }
 
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }    
}

class UserDAO extends JdbcDaoSupport {
	
	public User findUser(String userId) {
		return getJdbcTemplate().query("select id, user_id, password from bj_user_details where user_id=?",
				new Object[] { userId },
				new ResultSetExtractor<User>() {
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		});
	}
}

class LoginEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	public LoginEvent(User user) {
        super(user);
    }
 
    public User getUser() {
        return (User) getSource();
    }
}

class LoginHistoryListener implements ApplicationListener<LoginEvent> {
    @Autowired
    private LoginHistoryDAO loginHistoryDao;
    
    public void onApplicationEvent(LoginEvent loginEvent) {
        System.out.println("++++" + Thread.currentThread().getName()+ ": Entered LoginHistoryListener+++++");
        System.out.println("Event:" + loginEvent.getSource().getClass());
        loginHistoryDao.recordLoginHistory(loginEvent);
        System.out.println("++++" + Thread.currentThread().getName()+ ": Finished processing login event+++++");
    }
}

class LoginHistoryDAO extends JdbcDaoSupport {
	
    public void recordLoginHistory(LoginEvent loginEvent) {
        User user = loginEvent.getUser();
        System.out.println("Add entry to login history for " + user.getUserId());
		List<Map<String, Object>> i = getJdbcTemplate().queryForList("select BJ_SEQUENCE.nextval from dual");
        getJdbcTemplate().update("insert into bj_user_login_history(id,status, session_id, login_time, user_id) values(?,?, ?, ?, ?)",
        		i.iterator().next().get("NEXTVAL"),"SUCCESS", user.getSessionId(), user.getLoginTime(), user.getId());
    }
     
    public List<LoginHistory> findLoginHistory(final User user) {
        return getJdbcTemplate().query("select id, status, login_time, session_id from bj_user_login_history where user_id=?", 
                new Object[]{user.getId()}, 
                new RowMapper<LoginHistory>(){
 
            public LoginHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
                LoginHistory loginHistory = new LoginHistory();
                loginHistory.setId(rs.getInt("id"));
                loginHistory.setStatus(rs.getString("status"));
                loginHistory.setLoginTime(rs.getDate("login_time"));
                loginHistory.setSessionId(rs.getLong("session_id"));
                loginHistory.setUser(user);
                return loginHistory;
            }});
    }    
}


class LoginHistory {
    private int id;
    private User user;
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
 
    public User getUser() {
        return user;
    }
 
    public void setUser(User user) {
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

class User {
    private Integer id;
    private String userId;
    private String password;
    private Date loginTime;
    private Long sessionId;
 
    public User(Integer id, String userId, String password) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.sessionId = UUID.randomUUID().getMostSignificantBits();
        this.loginTime = new Date();
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
}

