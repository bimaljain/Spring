package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _008_MethodInjection_Lookup {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_008_MethodInjection_Lookup.xml");
		
		Singleton3 singleton = (Singleton3)ctx.getBean("singletonBean");
		for (int i=0;i<3;i++){
			System.out.println(singleton.getPrototype().getUrl());
		}
		
		Singleton4 singleton2 = (Singleton4)ctx.getBean("singletonBean2");
		for (int i=0;i<3;i++){
			System.out.println(singleton2.getPrototype().getUrl());
		}
	}
}

abstract class Singleton3 {	
	public Singleton3(){
		System.out.println("Singleton is created");
	}

	public abstract Prototype3 getPrototype();
}

class Singleton4 {	
	public Singleton4(){
		System.out.println("Singleton2 is created");
	}

	public Prototype3 getPrototype(){
		System.out.println("get prototype bean");
		return null;
	}
}

class Prototype3 {
	String url="http://localhost:8080";
	
	public Prototype3(){
		System.out.println("prototype bean is created");
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
}


