/*
If you have many setter methods then it is convenient to use p-namespace in the XML configuration file.

--------------------------------------------------------------------------
Setter Dependency Injection(SDI) vs Constructor Dependency Injection(CDI):
--------------------------------------------------------------------------
1. The way we Inject:
SDI usually happens after the object gets created by the container and by using the setter methods
Whereas CDI happens during the object creation by the container by passing dependencies as a parameter to the constructor.

2. Complete dependency injection vs Partial dependency injection:
Since CDI happens during constructor execution, we need to pass all the necessary parameters to that constructor, so complete dependency injection is 
mandatory Whereas SDI happens after constructor gets executed , so we can do either partial dependency injection or complete dependency injection 
based on the requirement.

3. Readability:
If we look at the readability of dependencies, we can prefer SDI, as in SDI we can see which property we are injecting based on the setter method 
and property tag.

4. Overriding injected value:
If we use both CDI and SDI , Spring container will override the CDI by SDI.
 */

package _001;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _004_SetterInjection_p_namespace {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_004_SetterInjection_p_namespace.xml");
		Person4 p = (Person4)ctx.getBean("person");
		System.out.println(p);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Person4 {
	private int id;
	private String name;
	private List<String> languages;
	private List<Address4> Address;

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
	
	public List<String> getLanguages() {
		return languages;
	}
	
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public List<Address4> getAddress() {
		return Address;
	}

	public void setAddress(List<Address4> Address4) {
		this.Address = Address4;
	}

	public String toString(){
		return id + " : " + name + " \n " 
				+ languages + " \n " 
				+ Address;
	}
}

class Address4 {
	private String streetName;
	private String city;
	private int zip;
	
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


