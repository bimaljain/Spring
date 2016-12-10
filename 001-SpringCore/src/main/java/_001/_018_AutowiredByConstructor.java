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

	//@Autowired fails by Type since there are 2 beans of Address9 type. Next it checks for qualifier, but there is none. So finally it autowires 
	//byName (spring bean with name address2). We don't care about property name.
	@Autowired 
	public _018_AutowiredByConstructor(Address9 address2){
		this.address=address2;
	}
	
	public String toString(){
		return address.toString();
	}
}
