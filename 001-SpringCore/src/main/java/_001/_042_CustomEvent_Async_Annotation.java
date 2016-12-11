/*
Annotation-driven event listener:
---------------------------------
To get a notification about an event (both Spring event and custom domain event) a component implementing ApplicationListener with onApplicationEvent 
has to be created. Starting with Spring 4.2 to be notified about the new event it is enough to annotate a method in any Spring component with 
@EventListener annotation.

	@EventListener
	public void blogModified(BlogModifiedEvent blogModifiedEvent) {
	    externalNotificationSender.blogModified(blogModifiedEvent);
	}

Under the hood Spring will create an ApplicationListener instance for the event with a type taken from the method argument. There is no limitation 
on the number of annotated methods in one class – all related event handlers can be grouped into one class.

@EventListener is a core annotation, no extra configuration is needed using Java config. Internally theEventListenerMethodProcessor registers 
an ApplicationListener instance with the event type inferred from the method signature.

Relaxed ApplicationEvent:
-------------------------
Historically ApplicationEventPublisher had only an ability to publish objects which inherited after ApplicationEvent. Starting with Spring 4.2 the 
interface has been extended to support any object type. In that case the object is wrapped in PayloadApplicationEventand sent through.

	//base class with Blog field - no need to extend `ApplicationEvent`
	class BaseBlogEvent {}
	 
	class BlogModifiedEvent extends BaseBlogEvent {}
	//somewhere in the code
	ApplicationEventPublisher publisher = (...);    //injected
	 
	publisher.publishEvent(new BlogModifiedEvent(blog)); //just plain instance of the event

Asynchronous event processing:
------------------------------
It is also worth to mention that@EventListener can be easily combined with @Async annotation to provide asynchronous event processing. The code in 
the particular event listener doesn’t block neither the main code execution nor processing by other listeners.
To make it work it is only required to enable asynchronous method execution in general in your Spring context/application with @EnableAsync or 
<task:annotation-driven /> in xml.

	@Configuration
	@EnableAsync
	public class AppConfig {
		@Bean
		public AsyncTask asyncTask() {
			return new AsyncTask();
		}
	}

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
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

public class _042_CustomEvent_Async_Annotation {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_042_CustomEvent_Async_Annotation.xml");
		String userId = "admin";
		String password = "admin";
		LoginManager2 loginManager = (LoginManager2) context.getBean("loginManager2");
		System.out.println("Login userId " + userId);
		User2 user = loginManager.login(userId, password);

		System.out.println("Log-in again");
		loginManager.login(userId, password);

		System.out.println("Login History");
		LoginHistoryDAO2 loginHistoryDao = (LoginHistoryDAO2) context.getBean("loginHistoryDao");
		List<LoginHistory2> loginHistoryList = loginHistoryDao.findLoginHistory(user);
		for (LoginHistory2 loginHistory : loginHistoryList) {
			System.out.println(loginHistory);
		}
		((AbstractApplicationContext)context).registerShutdownHook();
	}
}

//@Component
class LoginManager2 implements ApplicationContextAware {
	@Autowired
	private UserDAO2 userDao;
	private ApplicationContext context;

	public User2 login(String userId, String password) {
		User2 user = userDao.findUser(userId);
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
class Listeners {
    @Autowired
    private LoginHistoryDAO2 loginHistoryDao;

    @Async
    @EventListener
    public void userListener(User user) {
        System.out.println("++++" + Thread.currentThread().getName()+ ": Entered userListener +++++");
        loginHistoryDao.recordLoginHistory(user);
        System.out.println("++++" + Thread.currentThread().getName()+ ": Finished processing login event+++++");
    }  
    
    @Async
	@EventListener
	public void applicationEventListener(ApplicationEvent event) {
		System.out.println("Listening to event: " + event.getClass());		
	}
	
    @Async
	@EventListener
	public void contextStartedEventListener(ContextStartedEvent event) {
		System.out.println("Listening to ContextStartedEvent: " + event.getClass());		
	} 
	
    @Async
	@EventListener
	public void contextRefreshedEventListener(ContextRefreshedEvent event) {
		System.out.println("Listening to ContextRefreshedEvent: " + event.getClass());		
	}
}

class LoginHistoryDAO2 extends JdbcDaoSupport {
    public void recordLoginHistory(User user) {
        System.out.println("Add entry to login history for " + user.getUserId());
        getJdbcTemplate().update("insert into user_login_history(status, session_id, login_time, user_id) values(?, ?, ?, ?)",
        		"SUCCESS", user.getSessionId(), user.getLoginTime(), user.getId());
    }
     
    public List<LoginHistory2> findLoginHistory(final User2 user) {
        return getJdbcTemplate().query("select id, status, login_time, session_id from user_login_history where user_id=?", 
                new Object[]{user.getId()}, 
                new RowMapper<LoginHistory2>(){
 
            public LoginHistory2 mapRow(ResultSet rs, int rowNum) throws SQLException {
                LoginHistory2 loginHistory = new LoginHistory2();
                loginHistory.setId(rs.getInt("id"));
                loginHistory.setStatus(rs.getString("status"));
                loginHistory.setLoginTime(rs.getDate("login_time"));
                loginHistory.setSessionId(rs.getLong("session_id"));
                loginHistory.setUser(user);
                return loginHistory;
            }});
    }    
}

class UserDAO2 extends JdbcDaoSupport {
	
	public User2 findUser(String userId) {
		return getJdbcTemplate().query("select id, user_id, password from user_details where user_id=?",
				new Object[] { userId },
				new ResultSetExtractor<User2>() {
			public User2 extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return new User2(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		});
	}
}

class LoginHistory2 {
    private int id;
    private User2 user;
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
 
    public User2 getUser() {
        return user;
    }
 
    public void setUser(User2 user) {
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

class User2 {
    private Integer id;
    private String userId;
    private String password;
    private Date loginTime;
    private Long sessionId;
 
    public User2(Integer id, String userId, String password) {
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