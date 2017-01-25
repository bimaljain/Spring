/*
Circular Dependency:
You can handle circular dependencies with setter injection only and not with CDI.
 */

package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _010_CircularDependency_Issue {

	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_010_CircularDependency_Issue.xml");
		A a= (A)ctx.getBean("beanA");
		System.out.println(a.getB() );
	}
}

class A {
	private B b;
	
	public A(B b){
		this.b=b;
	}
	
	public A(){		
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}
	
	public String toString(){
		return "I am A";
	}
}

class B {
	private A a;
	
	public B(A a){
		this.a=a;
	}

	public B(){		
	}
	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
	
	public String toString(){
		return "I am B";
	}	
}
