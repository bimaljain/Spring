/*
-----------
Autowiring:
-----------
The Spring container can autowire relationships between collaborating beans without using <constructor-arg> and <property> elements which helps cut 
down on the amount of XML configuration you write for a big Spring based application.

Autowiring Modes:There are following autowiring modes which can be used to instruct Spring container to use autowiring for DI. You use the autowire 
attribute of the <bean/> element to specify autowire mode for a bean definition.

1. no: This is default setting which means no autowiring and you should use explicit bean reference for wiring.

2. byName: Spring container looks at the properties (actually setter methods) of the beans on which autowire attribute is set to byName in the XML 
configuration file. It then tries to match and wire its properties with the beans defined by the same names in the configuration file. The 
requirement is that the name of the member variables of the bean must match one bean id defined in the xml. If matches are found, it will inject 
those beans otherwise, it will throw exceptions.

3. byType: Spring container looks at the properties of the beans on which autowire attribute is set to byType in the XML configuration file. It then 
tries to match and wire a property if its type matches with exactly one of the beans name in configuration file. If more than one such beans exists, 
a fatal exception is thrown.

4. constructor: This mode is very similar to byType, but it applies to constructor arguments. Spring container looks at the beans on which autowire 
attribute is set to constructor in the XML configuration file. It then tries to match and wire its constructor's argument with exactly one of the 
beans name in configuration file. If matches are found, it will inject those beans otherwise, it will throw exceptions.

5. autodetect: Spring first tries to wire using autowire by constructor, if it does not work, Spring tries to autowire by byType.

Limitations with autowiring: 
Explicit wiring is recommended over autowiring. Though, autowiring can significantly reduce the need to specify properties or constructor arguments 
but you should consider the limitations/disadvantages of autowiring before using them.

1. Overriding possibility: You can still specify dependencies using <constructor-arg> and <property> settings which will always override autowiring.

2. Primitive data types: You cannot autowire simple properties such as primitives, Strings, and Classes.

3. Confusing nature: Autowiring is less exact than explicit wiring, so if possible prefer using explict wiring.

 */
package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _014_Autowiring_ByName {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_014_Autowiring_ByName.xml");
		System.out.println((Person6)ctx.getBean("person"));
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Person6 {
	/*autowire byName: 
	 1.) get all the setter methods.
	 2.) look for the setter method name (and nothing else in the setter method) Class properties also does not matter.
	 -- id, name, address69
	  
	 In this example, property name is id1, but setter method is setId().
	*/
	private int id1;
	private String name1;
	private Address6 address1;
	
	public int getId() {
		return id1;
	}

	public void setId(int id) {
		this.id1 = id;
	}

	public String getName() {
		return name1;
	}

	public void setName(String name) {
		this.name1 = name;
	}

	public Address6 getAddress() {
		return address1;
	}

	//setter method name matters
	public void setAddress69(Address6 address1) {
		this.address1 = address1;
	}

	public String toString(){
		return id1 + " : " + name1 + " : " + address1;
	}
}

class Address6 {
	private String streetName;
	private String city;
	private int zip;
	
	public Address6(String streetName, String city, int zip){
		this.streetName=streetName;
		this.city=city;
		this.zip=zip;
	}
	
	public String toString(){
		return streetName + " : " + city + " : " + zip;
	}	
}