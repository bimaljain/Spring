/*
Injecting Inner Beans:
As Java inner classes are defined within the scope of other classes, similarly, inner beans are beans that are defined within the scope of another 
bean. Thus, a <bean/> element inside the <property/> or <constructor-arg/> elements is called inner bean.

 */

package _002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _105_InnerBeans {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_105_InnerBeans.xml");
		Person2 person = (Person2)ctx.getBean("person");
		System.out.println(person);
		person = (Person2)ctx.getBean("person2");
		System.out.println(person);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Person2 {
	private Address2 address;

	public Person2(){
	}	
	public Person2(Address2 address){
		this.address=address;
	}
	
	public Address2 getAddress() {
		return address;
	}
	public void setAddress(Address2 address) {
		this.address = address;
	}

	public String toString(){
		return address +"" ;
	}
}

class Address2 {
	private String streetName;
	private String city;
	private int zip;
	
	public Address2(){
	}
	public Address2(String streetName, String city, int zip){
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
