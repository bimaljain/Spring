package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class _043_PublishListenPublish {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_043_PublishListenPublish.xml");
		LoginPublisher loginManager = (LoginPublisher) context.getBean("loginPublisher");
		loginManager.login();

		((AbstractApplicationContext)context).registerShutdownHook();
	}
}

@Component
class LoginPublisher implements ApplicationEventPublisherAware {
	private ApplicationEventPublisher publisher;

	public User login() {
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": ASYNC: Publishing successful login event------------");
		publisher.publishEvent(new LoginEvent3(new User3()));
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": ASYNC: Finished publishing login event--------------\n");
		return null;
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher=	applicationEventPublisher;
	}    
}

class LoginEvent3 extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	public LoginEvent3(User3 user) {
        super(user);
    }
 
    public User3 getUser() {
        return (User3) getSource();
    }
}

class LoginEvent4 extends ApplicationEvent {
	public LoginEvent4(User3 user) {
        super(user);
    }
 
    public User getUser() {
        return (User) getSource();
    }
}

@Component
class LoginHistoryListener3{

	@EventListener
	public LoginEvent4 onApplicationEvent(LoginEvent3 loginEvent) {
			System.out.println("inside first listener");	
			System.out.println("publishing another event");
			return new LoginEvent4(new User3());
	}
}

@Component
class LoginHistoryListener4 {

	@EventListener
	public void onApplicationEvent(LoginEvent3 loginEvent) {
			System.out.println("inside second listener");
	}
}

class User3 {	  
    public User3(){    	
    }     
}