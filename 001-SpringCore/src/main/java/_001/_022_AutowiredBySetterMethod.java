package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class _022_AutowiredBySetterMethod {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_017_Autowired.xml");
		System.out.println("Setter- "+ (_022_AutowiredBySetterMethod)ctx.getBean("_022_AutowiredBySetterMethod"));
	}

	private Address9 address;
	
	//Autowiring on property need a default constructor, otherwise you get below error
	//Failed to instantiate: No default constructor found
	//If you dont specify any constructor, you always get default constructor. I am still writing one below.
	public _022_AutowiredBySetterMethod(){
	}
	
	@Autowired
	public void setAddress(Address9 address) {
		this.address = address;
	}

	public String toString(){
		return address.toString();
	}
}
