/*
autowire="constructor" 
1.) it just looks at the constructor argument list.

It will check for type first. If the container cannot resolve by type, it will try to resolve by name (of the constructor args). If the container 
cannot resolve by name also, an exception will be thrown. Remember name of the class properties does not matter.

In the below example, there are 2 Address8 beans, so Spring will resolve by name=address2
 */
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
	private int id99;
	private String name99;
	private Address8 address99;

	public Person8(int id, String name, Address8 address2){
		this.id99=id;
		this.name99=name;
		this.address99=address2;	
	}
	
	public String toString(){
		return id99 + " : " + name99 + " : " + address99;
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