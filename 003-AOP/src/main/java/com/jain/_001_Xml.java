/*
------
ISSUE:
------
In object oriented programming, each object or class is made up of some properties and behavior. For example an Employee object might have 
properties like employee id, name etc and behavior like works.

public class Employee {
	int id;
	String name;
	public Employee(int id, String name)
	{
		this.id=id;
		this.name=name;
	}
	public void work()
	{
		System.out.println(“Employee:”+ name+” is working”);
	}
}

But in real world a lot of things are happening around objects, for example lets say a bookkeeper needs to take a note of everytime an employee 
starts working and finishes working.

public class BookKeeper {
	java.util.Date date= new java.util.Date();
	public void employeeStartsWorking()
	{
		System.out.println(“Employee has started working”+new Timestamp(date.getTime()));
	}
	public void employeeEndsWorking()
	{
		System.out.println(“Employee has ended working”+new Timestamp(date.getTime()));
	}
}

And my Employee class should be modified to

public class Employee {
	int id;
	String name;
	BookKeeper bookKeeper=new BookKeeper();
	public Employee(int id, String name)
	{
		this.id=id;
		this.name=name;
	}
	public void work()
	{
		bookKeeper.employeeStartsWorking();
		System.out.println(“Employee:”+ name+” is working”);
		bookKeeper.employeeEndsWorking();
	}
}

The challenge here is that my Employee should now be aware of BookKeeper and maintain its calls. Also it would need to actually handle error 
conditions (what if bookKeeper is null). AOP or Aspect oriented programming comes to rescue here. 

-----------------------
cross-cutting concerns:
-----------------------
In computing, aspect-oriented programming (AOP) is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting
concerns.
Cross cutting concerns are the functionalities which are distributed accross the application but are not part of any core module. So for example, we 
have employee class, manager class, consultant class all implemeting work and book keeper keeping record. So Book keeper needs to be available to all 
these objects, though it is not part of their core functionality. AOP recommends to move these functionality out of class and provides methods and 
tools for this. Some frequently occuring cases for AOP are logging (ever class has need for logging, but it is not part of core functionality), 
transaction management, authentication and authrization etc.

----------
AOP Terms:
----------
JoinPoint: A well defined point in execution of an application. A point where something is happening in the application. For example when a method is 
called, an object is instantiation, an exception is being thrown etc.
Advice: The code that is to be executed at a particular joinpoint.
Pointcut: Collection of joinpoints where advice has to be applied.
Aspect: Combination of pointcuts and advice, programmed in a class.
Weaving: When do we add aspects to actual code- compile time, run time (Spring), load time (AspectJ)
Target Object: object whose execution flow is modified by an AOP process.
Introduction: Process by which you modify target object, by declaring additional methods or fields.

Explaination using Restaurant Analogy: 
When you go out to a restaurant, you look at a menu and see several options to choose from. You can order one or more of any of the items on the 
menu. But until you actually order them, they are just "opportunities to dine". Once you place the order and the waiter brings it to your table, 
it's a meal. Join points are the options on the menu and pointcuts are the items you select. A joinpoint is an opportunity within code for you to 
apply an aspect...just an opportunity. Once you take that opportunity and select one or more joinpoints and apply an aspect to them, you've got a 
pointcut.

----------------
Types of advice:
----------------
Before: Before starting the execution
After: After completion of execution
After Throwing: After an exception is thrown
After Returning: After normal execution (no exception thrown)
Around: Before and After execution

----------------------------------
Dynamic Proxies vs. CGLib Proxies:
----------------------------------
Spring's AOP is proxy-based. Spring provides two different options to create the proxies. One is based on JDK dynamic proxies and works with 
interfaces, the other one utilizes CGLib and is based on classes. (That's why the property is called proxyTargetClass respectively proxy-target-class.) 
For the moment I just want to provide a quick summary on the pros and cons of both options:

JDK dynamic proxies:
1.) The class has to implement interfaces. Otherwise you will get ClassCastExceptions saying that $Proxy0 can not be casted to the particular class.
2.) Eventually dynamic proxies force you to program to interfaces since you can not cast the proxy to the class - a feature I really like about them.

CGLib proxies:
1.) The proxies are created by sub-classing the actual class. This means wherever an instance of the class is used it is also possible to use the 
CGLib proxy.
2.) The class needs to provide a default constructor, i.e. without any arguments. Otherwise you'll get an IllegalArgumentException: "Superclass has 
no null constructors but no arguments were given." This makes constructor injection impossible.
3.) The proxying does not work with final methods since the proxy sub class can not override the class' implementation.
4.) The CGLib proxy is final, so proxying a proxy does not work. You will get an IllegalArgumentException saying "Cannot subclass final class 
$Proxy0". But this feature is usually not needed anyway. (This issue might be solved in the future.)
5.) Since two objects are created (the instance of the class and the proxy as instance of a sub class) the constructor is called twice. In general 
this should not matter. I consider changing the class' state based on constructor calls a code smell anyway.
6.) You have CGLib as additional dependency.

Both options suffer from some issues (not really issues, but you have to be aware of them):
1.) Most important proxy-based AOP only works from "outside". Internal method calls are never intercepted.
2.) Second, the object has to be managed by the Spring container. Instantiating it yourself using new does not work.
3.) The proxies are not Serializable.

-----------
Approaches:
-----------
There are 2 approaches:
1.) XML based
2.) AspectJ based

----------------------
Spring AOP vs AspectJ:
----------------------
If you are using Spring framework, it is easy to implement Spring AOP. Ease of implementation comes with some limitations like you can only use 
aspects with spring managed beans and only methods can be used as pointcuts. Another limitation is that you cannot apply aspects for method calling 
another in same class. Whereas AspectJ does not have these limitations.
The reason behind this is the way Spring and AspectJ implements AOP. Spring uses runtime weaving whereas AspectJ uses load time weaving. Spring 
implements proxy based approach. Where it adds a proxy wrapper around your class, and every call to the class method, has to go through the proxy 
wrapper. AspectJ implement load time weaving by actually manipulating the bytecode using its compiler while class is loaded to JVM.

Spring AOP uses either JDK dynamic proxies or CGLIB to create the proxies for your target objects.
According to Spring documentation, in case your target implements at least one interface, a JDK dynamic proxy will be used. However if your target 
object does not implement any interfaces then a CGLIB proxy will be created. This is how you can force creation of the CGLIB proxies 
(set proxy-target-class="true"). 

 <aop:config proxy-target-class="true">
    <!-- other beans defined here... -->
 </aop:config>
 
Remember that if the target object does not implement any interface, then you must enable CGLIB by setting proxy-target-class="true" to get a proxy.

When using AspectJ and its autopoxy support you can also force CGLIB proxies. This is where the <aop:aspectj-autoproxy> is used and also here the 
"proxy-target-class" must be set to true:
<aop:aspectj-autoproxy proxy-target-class="true"/>

Remember that AspectJ automatically decides whether to use JDK proxy or CGLIB proxy, depending upon whether target implements an interface or not. 
No need to set  proxy-target-class="true" for CGLIB to work, but you can set it to true if you only wants CGLIB proxies.
 */

/*
This example is when target object implements an interface

Spring by default will create JDK Proxy object. So,
1.) <aop:config>: generates JDK proxy. Because proxy-target-class="false" by default.
2.) <aop:config proxy-target-class="false">: generates JDK proxy
3.) <aop:config proxy-target-class="true">: generates CGLIB proxy. "true" forces Spring to generate CGLIB proxy. (BTW, CGLIB=Code Generation Library)

We need to import AspectJ weaver jar for this example, though we are not using AspectJ. However Spring isn't using the AspectJ weaver jar in this 
case. It is simply reusing some of the classes from aspectjweaver.jar. It still uses dynamic proxies - doesn't do byte code modification.
 */

package com.jain;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class _001_Xml {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001.xml");
		IUser user = (IUser)ctx.getBean("user"); // proxy object should be casted to the interface and not to the impl class
		System.out.println(user.getClass());
		user.createUser();
		user.updateUser();
		try{
			user.printThrowException();
		}catch(Exception ex){			
		}
	}
}

interface IUser{
	public void createUser();
	public int updateUser();
	public void printThrowException();
}

//@Component
class User implements IUser{
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
class LogAdvice{
	public void beforeAdvice(){ 
		System.out.println("Going to setup user profile."); 
	}
	public void afterAdvice(){ 
		System.out.println("user profile has been setup."); 
	}
	public void afterReturningAdvice(Object retVal){ 
		if (retVal != null)
			System.out.println("Returning:" + retVal.toString() ); 
	}
	public void afterThrowingAdvice(IllegalArgumentException ex){ 
		System.out.println("There has been an exception: " + ex.toString()); 
	}	   
}