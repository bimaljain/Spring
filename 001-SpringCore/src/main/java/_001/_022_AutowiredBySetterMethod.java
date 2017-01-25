package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

//@Component //uncomment this first
public class _022_AutowiredBySetterMethod {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_013_BeanLifeCycle_Annotation.xml");
		System.out.println("Setter- "+ (_022_AutowiredBySetterMethod)ctx.getBean("_022_AutowiredBySetterMethod"));
	}

	private Address9 address99;
	
	//Autowiring on setter methods need a default constructor, otherwise you get below error
	//Failed to instantiate: No default constructor found
	//If you dont specify any constructor, you always get default constructor. I am still writing one below.
	public _022_AutowiredBySetterMethod(){
	}
	
	//@Autowired fails by Type since there are 2 beans of Address9 type. Next it checks for qualifier, but there is none. So finally it autowires 
	//byName (spring bean with name address2). We don't care about the class property name.
	@Autowired
	public void setAddress(Address9 address2) {
		this.address99 = address2;
	}

	public String toString(){
		return address99.toString();
	}
}

