package _001;

import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _007_MethodInjection_Replacer {
	public static void main(String[] args){
		ApplicationContext factory = new ClassPathXmlApplicationContext("_007_MethodInjection_Replacer.xml");
		for (int i=0;i<3;i++){
			Singleton5 singleton = (Singleton5)factory.getBean("singleton");
			System.out.println(singleton.getPrototype().getUrl());
		}
	}
}

class MethodReplacerImpl implements MethodReplacer{
	
	public MethodReplacerImpl() {
		System.out.println("Method Replacer is created");
	}

	public Object reimplement(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		return new Prototype();
	}
}

class Singleton5 {
	Prototype prototype;
	
	public Singleton5(Prototype prototype){
		this.prototype=prototype;
	}
	
	public Prototype getPrototype(){
		return prototype;
	}
}