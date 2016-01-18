package com.spring;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.AfterReturning;

@Aspect
public class Logging {
	   //Following is the definition for a pointcut to select all the methods available. So advice will be called for all the methods.
	   @Pointcut("execution(* com.spring.*.*(..))")
	   private void selectAll() { }
	   
	   @Before("execution(* com.spring.*.*(..))")
	   public void beforeAdvice(){ System.out.println("Going to setup student profile."); }

	   @After("execution(* com.spring.*.*(..))")
	   public void afterAdvice(){ System.out.println("Student profile has been setup."); }

	   @AfterReturning(pointcut = "execution(* com.spring.*.*(..))", returning="retVal")
	   public void afterReturningAdvice(Object retVal){ System.out.println("Returning:" + retVal.toString() ); }

	   @AfterThrowing(pointcut = "execution(* com.spring.*.*(..))", throwing = "ex")
	   public void AfterThrowingAdvice(IllegalArgumentException ex){ System.out.println("There has been an exception: " + ex.toString()); }	   
}