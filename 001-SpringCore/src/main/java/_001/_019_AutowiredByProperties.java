package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

//@Component //uncomment this first
public class _019_AutowiredByProperties {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_013_BeanLifeCycle_Annotation.xml");		
		System.out.println((_019_AutowiredByProperties)ctx.getBean("_019_AutowiredByProperties"));
	}
	
	//@Autowired fails by Type since there are 2 beans of Address9 type. Next it checks for qualifier, but there is none. So finally it autowires 
	//byName (spring bean with name address2). Also we don't need any setter method.
	@Autowired
	private Address9 address2;

	//Autowiring on property need a default constructor, otherwise you get below error
	//Failed to instantiate: No default constructor found
	//If you don't specify any constructor, you always get default constructor. I am still writing one below.
	public _019_AutowiredByProperties(){
	}	

	public String toString(){
		return address2.toString();
	}
}
