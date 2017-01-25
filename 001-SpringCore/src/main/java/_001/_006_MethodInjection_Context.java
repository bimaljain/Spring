package _001;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _006_MethodInjection_Context {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_006_MethodInjection_Context.xml");
		for (int i=0; i<3 ; i++){
			Singleton2 singleton = (Singleton2)ctx.getBean("singletonBean");
			System.out.println(singleton.getPrototype().getUrl());
		}
	}
}

class Singleton2 implements ApplicationContextAware{	
	//there is no need of setter methods since we are not injecting prototype from outside.
	private Prototype2 prototype;
	private ApplicationContext ctx;
	
	public Singleton2(){
		System.out.println("singleton bean is created");
	}

	public Prototype2 getPrototype(){
		prototype = (Prototype2)ctx.getBean("prototypeBean");
		return prototype;
	}

	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.ctx=arg0;		
	}
}

class Prototype2 {
	String url="http://localhost:8080";
	
	public Prototype2(){
		System.out.println("prototype bean is created");
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
}
