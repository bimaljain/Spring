package _001;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ResolvableType;

public class _044_SyncAsyncTogether {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_044_SyncAsyncTogether.xml");
		LoginPublisher6 loginManager = (LoginPublisher6) context.getBean("loginManager");
		LoginPublisher7 loginManager2 = (LoginPublisher7) context.getBean("loginManager2");
		loginManager.login();
		loginManager2.login();

		((AbstractApplicationContext)context).registerShutdownHook();
	}
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface AsyncListener {
} 

// To achieve this, we need to provide our own implementation of the ApplicationEventMulticaster
class DistributiveEventMulticaster implements ApplicationEventMulticaster {
    private ApplicationEventMulticaster asyncEventMulticaster;
    private ApplicationEventMulticaster syncEventMulticaster;
   
    public void addApplicationListener(ApplicationListener listener) {
        // choose multicaster by annotation
        if (listener.getClass().getAnnotation(AsyncListener.class) != null) {
            asyncEventMulticaster.addApplicationListener(listener);
        } else {
            syncEventMulticaster.addApplicationListener(listener);
        }
    }
    
    public void removeApplicationListener(ApplicationListener listener) {
        asyncEventMulticaster.removeApplicationListener(listener);
        syncEventMulticaster.removeApplicationListener(listener);
    }    
    
    public void removeAllListeners() {
        syncEventMulticaster.removeAllListeners();
        asyncEventMulticaster.removeAllListeners();
    }
    
    public void multicastEvent(ApplicationEvent event) {
        syncEventMulticaster.multicastEvent(event);
        asyncEventMulticaster.multicastEvent(event);
    }    
    
    public void addApplicationListenerBean(String listenerBeanName) {
    }
    
    public void removeApplicationListenerBean(String listenerBeanName) {
        // do nothing
    }    
	
	public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
		syncEventMulticaster.multicastEvent(event);
        asyncEventMulticaster.multicastEvent(event);		
	}

    // ******************** SETTERS ********************

    public void setAsyncEventMulticaster(ApplicationEventMulticaster asyncEventMulticaster) {
        this.asyncEventMulticaster = asyncEventMulticaster;
    }

    public void setSyncEventMulticaster(ApplicationEventMulticaster syncEventMulticaster) {
        this.syncEventMulticaster = syncEventMulticaster;
    }
}

class LoginEvent6 extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	public LoginEvent6(User6 user) {
        super(user);
    }
 
    public User6 getUser() {
        return (User6) getSource();
    }
}

class LoginEvent7 extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	public LoginEvent7(User6 user) {
        super(user);
    }
 
    public User6 getUser() {
        return (User6) getSource();
    }
}

class LoginPublisher6 implements ApplicationEventPublisherAware {
	private ApplicationEventPublisher publisher;

	public User6 login() {
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": ASYNC: Publishing successful login event------------");
		publisher.publishEvent(new LoginEvent6(new User6()));
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": ASYNC: Finished publishing login event--------------\n");
		return null;
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher=	applicationEventPublisher;
	}    
}

class LoginPublisher7 implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;

	public User6 login() {
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": SYNC: Publishing successful login event2------------");
		publisher.publishEvent(new LoginEvent7(new User6()));
		System.out.println("-----------------" +Thread.currentThread().getName()+ ": SYNC: Finished publishing login event2--------------\n");
		return null;
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher=	applicationEventPublisher;
	}    
}

@AsyncListener
class LoginHistoryListener6 implements ApplicationListener<LoginEvent6> {

	public void onApplicationEvent(LoginEvent6 loginEvent) {
		try{
			System.out.println("++++" + Thread.currentThread().getName()+ ": ASYNC: Entered LoginHistoryListener+++++");
			Thread.sleep(3000);
			System.out.println("++++" + Thread.currentThread().getName()+ ": ASYNC: Finished processing login event+++++");
		}catch(Exception e){    		
		}
	}
}

class LoginHistoryListener7 implements ApplicationListener<LoginEvent7> {

	public void onApplicationEvent(LoginEvent7 loginEvent) {
		try {			
			System.out.println("++++" + Thread.currentThread().getName()+ ": SYNC: Entered LoginHistoryListener+++++");
			Thread.sleep(3000);
			System.out.println("++++" + Thread.currentThread().getName()+ ": SYNC: Finished processing login event+++++");
		} catch (InterruptedException e) {
		}
	}
}


class User6 {  
    public User6(){    	
    }     
}