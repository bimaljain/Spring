/*
@Qualifier: 
There may be a situation when you create more than one bean of the same type and want to wire only one of them with a property, in such case you 
can use @Qualifier annotation along with @Autowired to specify the exact bean to be wired.
 */
package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

//@Component //uncomment this first
public class _021_AutowiredByProperties {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_013_BeanLifeCycle_Annotation.xml");
		System.out.println((_021_AutowiredByProperties)ctx.getBean("_021_AutowiredByProperties"));
	}
	
	//@Autowired fails by Type since there are 2 beans of Address type. Next it checks for qualifier, and finds a bean with id=address2
	@Autowired
	@Qualifier("address2")
	private Address9 address;

	public String toString(){
		return address.toString();
	}
}
