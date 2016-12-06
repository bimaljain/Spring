/*
Dependency Injection Type & Description
DI exists in two major variants:

Constructor-based DI: 
Constructor-based DI is accomplished when the container invokes a class constructor with a number of arguments, each representing a dependency on other 
class. < constructor-arg > tag is used to inject the values through constructor

Setter-based DI: 
Setter-based DI is accomplished by the container calling setter methods on your beans after invoking a no-argument constructor or no-argument static 
factory method to instantiate your bean. < property > tag is used to inject the values through setter.

In both the case, if you are passing a reference to an object, you need to use "ref" attribute of <constructor-arg> or <property> tag and if you 
are passing a value directly then you should use "value" attribute.

You can mix both, Constructor-based and Setter-based DI but it is a good rule of thumb to use constructor arguments for mandatory dependencies and 
setters for optional dependencies.

Constructor-based DI:
Constructor arguments resolution: <constructor-arg> always takes a String and Spring takes care of converting them to the datatype of the constructor’s 
arguments. There may be ambiguity exist while passing arguments to the constructor in case there are more than one parameters or incase of overloaded 
constructors. To resolve this ambiguity, there are many options:

1. The order in which the constructor arguments are defined in a bean definition is the order in which those arguments are supplied to the appropriate 
constructor. 

2. Using type attribute of constructor-arg

3. Finally and the best way to pass constructor arguments, use the index attribute to specify explicitly the index of constructor arguments. Here the 
index is 0 based.
 */
package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _001_ConstructorInjection {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_ConstructorInjection.xml");
		Person p = (Person)ctx.getBean("person");
		System.out.println(p);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Person {
	private int id;
	private String name;
	private Address address;

	public Person(int id, String name, Address address){
		this.id=id;
		this.name=name;
		this.address=address;	
	}
	
	public String toString(){
		return id + " : " + name + " : " + address;
	}
}

class Address {
	private String streetName;
	private String city;
	private int zip;
	
	public Address(String streetName, String city, int zip){
		this.streetName=streetName;
		this.city=city;
		this.zip=zip;
	}
	
	public String toString(){
		return streetName + " : " + city + " : " + zip;
	}
	
}

