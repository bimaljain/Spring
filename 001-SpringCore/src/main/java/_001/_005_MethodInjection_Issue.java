/*
-----------------
Method Injection:
-----------------
Spring container resolves the dependencies at instantiation time which means if any singleton bean has a dependency of any prototype bean, then a 
new object of prototype bean will be instantiated and injected in to singleton bean at the time of instantiation of Singleton bean. With this, the 
same prototype bean will always be supplied from singleton bean. So we need a different approach when a Singleton bean has a dependency on Prototype 
bean.

1. Using Application Context Aware approach:
We discussed Application Context Aware interface in earlier chapters with which we can get the instance of application context in which the bean is 
configured. Every time a call getBean() for a prototype object will returns a different bean so Implements Application Context Aware interface in a 
singleton bean. In the getter method of prototype bean, we can explicitly make a call to getBean and returns the object.

Issue with this approach is that now you are dependent on the applicationContext.

2. Using Method Injection:
Method Injection should be used when a Singleton bean has a dependency on Prototype bean.
Spring provides two methods for Method Injection - Lookup method Injection and Arbitrary method replacement.

Arbitrary method replacement:
-----------------------------
Consider the scenario where you don't have the source code of a particular bean and you want to change the implementation of one of the methods in that? 
There are many ways to solve this problem. The very common solution is to extend that particular bean class and override the method you want to change 
and then plugin the new implementation to the Spring context. Arbitrary method replacement is another solution to this problem.

Arbitray method replacement will give you the power to replace arbitrary methods in the managed beans to another method implementation. This is 
achieved by implementing the interface org.springframework.beans.factory.support.MethodReplacer.

Spring provides below tag that can be used for method injection.
	<replaced-method name="method_name" replacer="Bean_Implementing_MethodReplacer" />
	
Lookup method Injection:
------------------------
A lookup method causes the IoC container to override the given  method and return the bean with the name given in the bean  attribute.
We need to create an abstract method  in Singleton class with return type as  PrototypeBean

Spring provides below tag that can be used for method injection.
       <lookup method name="method_name” value=”prototype_bean”/>

 */
package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _005_MethodInjection_Issue {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_005_MethodInjection_Issue.xml");
		for (int i=0; i<3 ; i++){
			Singleton singleton = (Singleton)ctx.getBean("singletonBean");
			System.out.println(singleton.getPrototype().getUrl());
		}
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Singleton {	
	private Prototype prototype;
	
	public Singleton(){
		System.out.println("singleton bean is created");
	}

	public Prototype getPrototype(){
		return prototype;
	}
	public void setPrototype(Prototype prototype) {
		this.prototype = prototype;
	}
}

class Prototype {
	String url="http://localhost:8080";
	
	public Prototype(){
		System.out.println("prototype bean is created");
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
}
