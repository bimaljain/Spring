/*
autowire byType:
1.) get all the setter methods.
2.) looks for type (from the arguments) in the setter methods. (and nothing else in the setter method). Class properties also does not matter.
-- int, String, Address7

If you define 2 beans of same type, autowire byType will fail.
 */
package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _015_Autowiring_ByType {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_015_Autowiring_ByType.xml");
		System.out.println((Person7)ctx.getBean("person"));
	}
}

class Person7 {
	private int id99;
	private String name99;
	private Address7 address99;
	
	public int getId() {
		return id99;
	}

	public void setId(int id) {
		this.id99 = id;
	}

	public String getName() {
		return name99;
	}

	public void setName(String name) {
		this.name99 = name;
	}

	public Address7 getAddress() {
		return address99;
	}

	//name of the setter method does not matter
	public void setAddress79(Address7 address69) {
		this.address99 = address69;
	}

	public String toString(){
		return id99 + " : " + name99 + " : " + address99;
	}
}

class Address7 {
	private String streetName;
	private String city;
	private int zip;
	
	public Address7(String streetName, String city, int zip){
		this.streetName=streetName;
		this.city=city;
		this.zip=zip;
	}
	
	public String toString(){
		return streetName + " : " + city + " : " + zip;
	}	
}

