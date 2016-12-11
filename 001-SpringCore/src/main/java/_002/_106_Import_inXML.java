/*
@Import: The @Import annotation allows for loading @Bean definitions from another configuration class.
 */

package _002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _106_Import_inXML {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_106_Import_inXML_person.xml");
		Person3 person = (Person3)ctx.getBean("person106");
		System.out.println(person);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}


class Person3 {
	private int id;
	private String name;
	private Address2 address;

	public Person3(){
	}
	
	public Person3(int id, String name, Address2 address){
		this.id=id;
		this.name=name;
		this.address=address;
	}
	
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
	public Address2 getAddress() {
		return address;
	}
	public void setAddress(Address2 address) {
		this.address = address;
	}
	
	public String toString(){
		return id + " : " + name + " : " + address ;
	}
}

