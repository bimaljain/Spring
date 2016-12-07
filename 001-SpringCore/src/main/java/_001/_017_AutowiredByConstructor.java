/*
Annotation Based Configuration
1. Starting Spring 2.5 it became possible to configure the DI using annotations. So instead of using XML to describe a bean wiring, you can move the 
bean configuration into the component class itself.
2. Before we can use annotation-based wiring, we will need to enable it in conf file using <context:annotation-config/> tag.
3. Annotation injection is performed before XML injection.

----------
@Autowired
----------
The @Autowired annotation provides more fine-grained control over where and how autowiring should be accomplished. The @Autowired annotation can be 
used to autowire bean on the setter method just like @Required annotation, constructor, a property or methods with arbitrary names and/or multiple 
arguments. @Autowired is "byType" DI first. If it fails, it tries by qualifier. If this also fails it finally tries "byName" DI.

1. @Autowired on Setter Methods: No <property> element in XML configuration file required. It tries to perform byType autowiring on the method.
2. @Autowired on Properties: You can use @Autowired annotation on properties to get rid of the setter methods.
3. @Autowired on Constructors: No <constructor-arg> elements in XML conf file required.
(note that above 3 points are not needed if you are annotating your classes with @Component and using <context:component-scan base-package="com.spring"/>)

----------
@Component
----------
1. Class annotated with @Component tells spring that the class is a bean. So Spring will register the class as bean with same name as the class name 
with first letter changed to small case.
2. You have to tell Spring that you have beans inside your code, by using:
<context:component-scan base-package="com.spring" />
3. With annotations you cannot have different instances of the same class. This is only possible when we use xml to configure the beans with different 
values.

<context:component-scan base-package="com.spring" /> checks for many annotations other than @Component, like, Stereotype. Stereotype provide special 
roles to the beans.
@Service: This tells spring that the bean is a service bean
@Repository: This tells spring that the bean is a data bean
@Controller: This tells spring that the bean is the C of the MVC

-------------------------------------------------------
<context:annotation-config> vs <context:component-scan>
-------------------------------------------------------
<context:annotation-config> is used to activate annotations in beans already registered in the application context (no matter if they were defined 
with XML or by package scanning).

<context:component-scan> can also do what <context:annotation-config> does but <context:component-scan> also scans packages to find and register 
beans within the application context.

----------------------------------------------------
@Component vs @Controller vs @Repository vs @Service
----------------------------------------------------
Similarity:
First point worth highlighting again is that with respect to scan-auto-detection and dependency injection for BeanDefinition all these annotation 
(viz., @Component, @Service, @Repository, @Controller) are all the same. You can use one in place of another and can still get your way around.

Differences:
@Component
This is a general-purpose stereotype annotation indicating that the class is spring component. 
What’s special about @Component? <context:component-scan> only scans @Component and do not looks for @Controller, @Service and @Repository in general. 
They are scanned because they themselves are annotated with @Component.

Just take a look at @Controller, @Service and @Repository annotation definition

@Component
public @interface Service {
    ….
}
 

@Component
public @interface Repository {
    ….
}
 

@Component
public @interface Controller {
    …
}

Thus it’s not wrong to say that @Controller, @Service and @Repository are special type of @Component annotation. <context:component-scan> picks them 
up and registers their following classes as beans, just as if they were annotated with @Component.
They are scanned because they themselves are annotated with @Component annotation. If you define your own custom annotation and annotate it with 
@Component, then it will also get scanned with <context:component-scan>


@Repository
This is to indicate that the class defines a data repository.
What’s special about @Repository? In addition to point out that this is an Annotation based Configuration, @Repository’s job is to catch Platform 
specific exceptions and re-throw them as one of Spring’s unified unchecked exception. And for this, we’re provided with 
PersistenceExceptionTranslationPostProcessor, that we’re required to add in our Spring’s application context like this:

<bean class=”org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor”/>
This bean post processor adds an advisor to any bean that’s annotated with @Repository so that any platform specific exceptions are caught and then 
rethrown as one of Spring’s unchecked data access exceptions.


@Controller
The @Controller annotation indicates that a particular class serves the role of a controller. The @Controller annotation acts as a stereotype for 
the annotated class, indicating its role.
What’s special about @Controller? You cannot switch this annotation with any other like @Service or @Repository, even though they look same. The 
dispatcher scans the classes annotated with @Controller and detects @RequestMapping annotations within them. You can only use @RequestMapping on 
@Controller annotated classes.


@Service
@Services hold business logic and call method in repository layer.
What’s special about @Service? Apart from the fact that it is used to indicate that it's holding the business logic, there’s no noticeable specialty 
that this annotation provides, but who knows, spring may add some additional exceptional in future.

*/

package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@Component //uncomment to check the error
//@Controller
//@Repository
//@Service
public class _017_AutowiredByConstructor {
	
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_013_BeanLifeCycle_Annotation.xml");
		System.out.println((_017_AutowiredByConstructor)ctx.getBean("_017_AutowiredByConstructor"));	
	}
	
	private Address9 address;

	//This will throw exception: 
	//java.lang.IllegalStateException: Autowired annotation requires at least one argument
	@Autowired
	public _017_AutowiredByConstructor(){
	}
	
	public String toString(){
		return address.toString();
	}
}

class Address9 {
	private String streetName;
	private String city;
	private int zip;
	
	public Address9(String streetName, String city, int zip){
		this.streetName=streetName;
		this.city=city;
		this.zip=zip;
	}
	
	public String toString(){
		return streetName + " : " + city + " : " + zip;
	}
}
