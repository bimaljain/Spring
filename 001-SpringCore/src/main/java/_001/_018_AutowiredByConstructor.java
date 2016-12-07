package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

//@Component //uncomment this first
public class _018_AutowiredByConstructor {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_013_BeanLifeCycle_Annotation.xml");
		System.out.println((_018_AutowiredByConstructor)ctx.getBean("_018_AutowiredByConstructor")); 		
	}
	
	private Address9 address;

	@Autowired 
	public _018_AutowiredByConstructor(Address9 address){
		this.address=address;
	}
	
	public String toString(){
		return address.toString();
	}
}
