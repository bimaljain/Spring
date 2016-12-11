/*
Publishing events in response to
Another nice thing with @EventListener is the fact that in a situation of non-void return type Spring will automatically publish returned event.
You can publish an event as a result of handling another event by returning an instance of the new event or a collection of the new events in case 
you want to publish multiple events.
 */

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
		LoginPublisher loginPublisher = (LoginPublisher) context.getBean("loginPublisher");
		loginPublisher.login();
		((AbstractApplicationContext)context).registerShutdownHook();
	}
}

@Component
class LoginPublisher implements ApplicationEventPublisherAware {
	private ApplicationEventPublisher publisher;

	public void login() {
		System.out.println("publishing first event");
		publisher.publishEvent(new LoginEvent3(new User3()));
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
			System.out.println("publishing second event");
			return new LoginEvent4(new User3());
	}
}

@Component
class LoginHistoryListener4 {

	@EventListener
	public void onApplicationEvent(LoginEvent4 loginEvent) {
			System.out.println("inside second listener");
	}
}

class User3 {	  
    public User3(){    	
    }     
}