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
	private int id;
	private String name;
	private Address7 address;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address7 getAddress() {
		return address;
	}

	public void setAddress(Address7 address) {
		this.address = address;
	}

	public String toString(){
		return id + " : " + name + " : " + address;
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

