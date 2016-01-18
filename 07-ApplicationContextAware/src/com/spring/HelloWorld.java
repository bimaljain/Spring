package com.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HelloWorld implements ApplicationContextAware, BeanNameAware {
	private String message;
	private ApplicationContext context;

	public void setMessage(String message){ this.message  = message; }
	public void getMessage(){ System.out.println("Your Message : " + message); }
	
	@Override
	public void setBeanName(String beanName) {
		System.out.println("Bean Name is " + beanName);
		
	}
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		System.out.println("Context is provided to HelloWorld pojo");
		this.context = context;
		
	}
}