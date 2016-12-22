/*
This example is when target object implements an interface

Requirement:
1.) Need AspectJ related dependencies
2.) Enable AspectJ: <aop:aspectj-autoproxy />
 */

package com.jain;

import java.sql.SQLException;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class _003_AspectJ {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_003.xml");
		IUser003 user = (IUser003)ctx.getBean("user003");
		System.out.println(user.getClass());
		user.createUser();
		user.updateUser();
		try{
			user.printThrowException();
		}catch(Exception ex){			
		}
	}
}

interface IUser003{
	public void createUser();
	public int updateUser();
	public void printThrowException();
}

//@Component
class User003 implements IUser003{
	public void createUser() {
		System.out.println("creating user");
	}
	public int updateUser() {
		System.out.println("updating user");		
		return 7;
	}	
	public void printThrowException(){
		System.out.println("Exception raised");
		throw new IllegalArgumentException();
	}
}

//@Component
@Aspect
class LogAdvice003{
	@Before("execution(* com.jain.*.*(..))")
	public void beforeAdvice(){ 
		System.out.println("Going to setup user profile."); 
	}
	@After("execution(* com.jain.*.*(..))")
	public void afterAdvice(){ 
		System.out.println("user profile has been setup."); 
	}
	@AfterReturning(pointcut="execution(* com.jain.*.*(..))", returning="retVal")
	public void afterReturningAdvice(Object retVal){ 
		if (retVal != null)
			System.out.println("Returning:" + retVal.toString() ); 
	}
	@AfterThrowing(pointcut="execution(* com.jain.*.*(..))", throwing = "ex")
	public void afterThrowingAdvice(IllegalArgumentException ex){ 
		System.out.println("There has been an exception: " + ex.toString()); 
	}	   
}