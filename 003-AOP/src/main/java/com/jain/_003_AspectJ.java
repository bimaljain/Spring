/*
Here we are only using AspectJ annotations in Spring AOP but not suing AspectJ AOP itself.
This example is when target object implements an interface.
Passing parameters to AOP advices: In Spring AOP it is possible to pass parameters from the intercepted method into the advice.

Requirement:
1.) Need AspectJ related dependencies
2.) Enable AspectJ: <aop:aspectj-autoproxy />
 */

package com.jain;

import java.sql.SQLException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class _003_AspectJ {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_003.xml");
		IUser003 user = (IUser003)ctx.getBean("user003");
		System.out.println(user.getClass());
		user.createUser();
		user.createUser2(33,"bimal");
		user.updateUser();		
		try{
			user.printThrowException();
		}catch(Exception ex){			
		}
	}
}

interface IUser003{
	public void createUser();
	public void createUser2(int userId, String userName);
	public int updateUser();
	public void printThrowException();	
}

@Component
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
	public void createUser2(int userId, String userName) {
		System.out.println("creating user: " + userId + userName);
	}
}

@Component
@Aspect
class LogAdvice003{
	@Pointcut("execution(* com.jain.*.*(..))")
	private void pointcutDef(){}
	
	//using pointcut reference
	@Before("pointcutDef()")
	public void beforeAdvice(){ 
		System.out.println("Going to setup user profile."); 
	}
	
	//using in-place pointcut
	//The advice will intercept all calls to any Spring bean method which in turn takes at least a parameter of type int. 
	//Here the int parameter should be the first parameter. 
	@Before("execution(* com.jain.*.*(..)) && args(userId,..)")
	public void beforeAdvice2(int userId){ 
		System.out.println("Going to setup user profile: " + userId); 
	}

	//The JoinPoint will be injected by the Spring container and may be used to extract information from the method being intercepted.
	//order must be maintained, else this does not work
	@Before("execution(* com.jain.*.*(..)) && args(userId,userName)") 
	public void beforeAdvice4(JoinPoint jp, int userId, String userName){ 
		System.out.println("Going to setup user profile: " + userId + userName);
		System.out.println("Method: " + jp.getSignature().getName());
		System.out.println("Class: " + jp.getTarget().getClass().getName());
	}
	
	@Pointcut("execution(* com.jain.*.*(..)) && args(userId,..)")
	private void pointcutDef2(int userId){}
	
	@Before("pointcutDef2(userId)")
	public void beforeAdvice5(int userId){ 
		System.out.println("Going to setup user profile: " + userId); 
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