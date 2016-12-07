package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

//@Component //uncomment this first
public class _020_AutowiredByProperties {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_013_BeanLifeCycle_Annotation.xml");
		System.out.println((_020_AutowiredByProperties)ctx.getBean("_020_AutowiredByProperties"));
	}
	
	//@Autowired fails by Type since there are 2 beans of Address type. Next it checks for qualifier, but there is none. So finally it autowires byName
	@Autowired
	private Address9 address2;

	public String toString(){
		return address2.toString();
	}
}
