/*
<context:component-scan> only scans @component. What if you want Spring to scan something else as well.

include-filter:
Controls which eligible types to include for component scanning. Note that these filters will be applied in addition to the default filters, if 
specified. Any type under the specified base packages which matches a given filter will be included, even if it does not match the default 
filters (i.e. is not annotated with @Component).

exclude-filter:
Controls which eligible types to exclude for component scanning.
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


