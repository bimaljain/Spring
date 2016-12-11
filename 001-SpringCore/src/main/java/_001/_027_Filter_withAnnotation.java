/*
When we use spring component scan either in JavaConfig or XML configuration, all the classes annotated by @Component, @Service, @Repository and 
@Controller stereotypes are by default auto detected. Now using spring filter, we can extend the ability of component scan. Using filter we can 
include those classes to auto detect which are not annotated by stereotype annotations and at the same time we can exclude those classes not to 
auto detect which are annotated by stereotype annotations. Here we will provide complete example of spring filter to customize component scan.

Component Scan Filter Types:
----------------------------
1.) annotation: Component scan of classes can be included and excluded by configuring annotation name applied at class level. 
2.) assignable: Using interface name or class name that has been implemented or extended by the classes, we can include and exclude classes in 
component scanning. 
3.) aspect: Using aspect expression targeting classes names, it can be included and excluded in component scanning. 
4.) regex: Using regex expression targeting classes names, it can be included and excluded in component scanning. 
5.) custom: We can also create custom filter type by implementing org.springframework.core.type.TypeFilter.

include-filter:
Controls which eligible types to include for component scanning. Note that these filters will be applied in addition to the default filters, if 
specified. Any type under the specified base packages which matches a given filter will be included, even if it does not match the default 
filters (i.e. is not annotated with @Component).

exclude-filter:
Controls which eligible types to exclude for component scanning.

Include vs Exclude Filter priority: first filter inside xml must be include always. If we put exclude first then we will end up with exception.
This is because include filter tries to take the beans registration but exclude filter overrides the same and exclude those beans from the 
registration. Hence no bean is registered and hence the exception.

 */
package _001;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

public class _027_Filter_withAnnotation {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_027_Filter_withAnnotation.xml");
		
		try{
			Service a=(Service)ctx.getBean(Service.class);
			a.doSomethingService();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
		
		try{
			Util b=(Util)ctx.getBean(Util.class);
			b.doSomethingUtil();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
		
		try{
			DAO c=(DAO)ctx.getBean(DAO.class);
			c.doSomethingDAO();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
	}

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MyAnnotation {

} 

@MyAnnotation
class Service {
	public void doSomethingService(){
		System.out.println("Service is doing something");
	}
}

//this will be excluded, even though @Repository is annotated with @Component
@Repository
class Util {
	public void doSomethingUtil(){
		System.out.println("Util is doing something");
	}
}

//this is not annotated at all, so this will be excluded as well.
class DAO {
	public void doSomethingDAO(){
		System.out.println("DAO is doing something");
	}

}


