package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class _019_AutowiredByProperties {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_017_Autowired.xml");		
		System.out.println((_019_AutowiredByProperties)ctx.getBean("_019_AutowiredByProperties"));
	}
	
	@Autowired
	private Address9 address;

	//Autowiring on property need a default constructor, otherwise you get below error
	//Failed to instantiate: No default constructor found
	//If you dont specify any constructor, you always get default constructor. I am still writing one below.
	public _019_AutowiredByProperties(){
	}	

	public String toString(){
		return address.toString();
	}
}
