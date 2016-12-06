package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _016_Autowiring_Constructor {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_016_Autowiring_Constructor.xml");
		System.out.println((Person8)ctx.getBean("person"));
	}
}

class Person8 {
	private int id;
	private String name;
	private Address8 address;

	public Person8(int id, String name, Address8 address){
		this.id=id;
		this.name=name;
		this.address=address;	
	}
	
	public String toString(){
		return id + " : " + name + " : " + address;
	}
}

class Address8 {
	private String streetName;
	private String city;
	private int zip;
	
	public Address8(String streetName, String city, int zip){
		this.streetName=streetName;
		this.city=city;
		this.zip=zip;
	}
	
	public String toString(){
		return streetName + " : " + city + " : " + zip;
	}
	
}