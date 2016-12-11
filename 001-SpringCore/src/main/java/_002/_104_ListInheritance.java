/*
List Inheritance:
Incase of List Inheritance, if you want to add more list elements in the child beans, you must set merge="true" else it will replace the list in 
the child beans.
 */
package _002;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _104_ListInheritance {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_104_ListInheritance.xml");
		Person person = (Person)ctx.getBean("person");
		System.out.println(person);
		Person person_merged = (Person)ctx.getBean("person_merged");
		System.out.println(person_merged);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Person {
	private int id;
	private String name;
	private List<Address> addresses;

	public Person(){
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
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public String toString(){
		return id + " : " + name + " : " + addresses ;
	}
}
