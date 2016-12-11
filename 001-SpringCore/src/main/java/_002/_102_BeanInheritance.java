/*
----------------------------
Bean Definition Inheritance:
----------------------------
Spring Bean definition inheritance has nothing to do with Java class inheritance but inheritance concept is same. You can define a parent bean 
definition as a template and other child beans can inherit required configuration from the parent bean, using the parent attribute, specifying 
the parent bean as the value of this attribute. The child definition can override some values, or add others, as needed.
Bean definition inheritance is not bean inheritance, just bean definition inheritance. 
If you have many bean definitions, and you want to initialize common set of values across all the beans, you can create a bean definition template 
with these common values, which you can inherit in all you beans.

 */

package _002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _102_BeanInheritance {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_102_BeanInheritance.xml");
		Address baseAddress = (Address)ctx.getBean("baseAddress");
		System.out.println(baseAddress);
		Address address = (Address)ctx.getBean("address");
		System.out.println(address);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Address {
	private String streetName;
	private String city;
	private int zip;
	
	public Address(){
	}
	public Address(String streetName, String city, int zip){
		this.streetName=streetName;
		this.city=city;
		this.zip=zip;
	}
	
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String toString(){
		return streetName + " : " + city + " : " + zip;
	}	
}
