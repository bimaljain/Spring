/*
This example is when target object does not implements any interface
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

public class _004_AspectJ {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_003.xml");
		User004 user = (User004)ctx.getBean("user004");
		System.out.println(user.getClass());
		user.createUser();
		user.updateUser();
		try{
			user.printThrowException();
		}catch(Exception ex){			
		}
	}
}

//@Component
class User004 {
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
class LogAdvice004{
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