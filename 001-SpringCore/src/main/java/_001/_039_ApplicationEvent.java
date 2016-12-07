package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _039_ApplicationEvent {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("_039_ApplicationEvent.xml");
		
		// start() creates ContextStartedEvent() and publish it.
		((AbstractApplicationContext) context).start();    // Let us raise a start event.		
		((AbstractApplicationContext) context).refresh();    // Let us raise a refresh event.  		
		((AbstractApplicationContext) context).stop();    // Let us raise a stop event.  
		((AbstractApplicationContext) context).close();    // Let us raise a close event. 
	}
}

class GenericEventListener implements ApplicationListener<ApplicationEvent>{
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println(event.getSource().getClass() + " recieved");		
	}
}

class ContextStartedEventListener implements ApplicationListener<ContextStartedEvent>{
	public void onApplicationEvent(ContextStartedEvent event) {
		System.out.println("ContextStartedEvent recieved");		
	}
}

class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent>{
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("ContextRefreshedEvent recieved");		
	}
}

class ContextStoppedEventListener implements ApplicationListener<ContextStoppedEvent>{
	public void onApplicationEvent(ContextStoppedEvent event) {
		System.out.println("ContextStoppedEvent recieved");		
	}
}

class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent>{
	public void onApplicationEvent(ContextClosedEvent event) {
		System.out.println("ContextClosedEvent recieved");		
	}
}
