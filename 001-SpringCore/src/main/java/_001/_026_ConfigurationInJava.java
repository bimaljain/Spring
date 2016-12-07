/*
@Configuration: Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions 
and service requests for those beans at runtime.

@Bean:  @Bean annotation tells Spring that a method annotated with @Bean will return an object that should be registered as a bean in the Spring 
application context. 

 */
package _001;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

public class _026_ConfigurationInJava {
	public static void main(String[] args){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
		Person10 p = (Person10)ctx.getBean("person");
		System.out.println(p);

		Person10 p2 = (Person10)ctx.getBean("person2");
		System.out.println(p2);
	}
}

//@Configuration //uncomment this first
class JavaConfig {
	
	@Bean(initMethod = "init", destroyMethod = "destroy" )
	@Scope("singleton")
	public Address10 address(){
		return new Address10("Fremont St","Fremont",94538);
	}	
	
	@Bean(name="person", initMethod = "init", destroyMethod = "destroy" )
	public Person10 person(){
		return new Person10(address());
	}
	
	@Bean(name="person2", autowire=Autowire.BY_NAME, initMethod = "init", destroyMethod = "destroy" )
	public Person10 person2(){
		return new Person10();
	}
}

class Person10 {

	private Address10 address;
	
	public Person10(Address10 address){
		this.address=address;
	}

	public Person10() {
		// TODO Auto-generated constructor stub
	}

	public Address10 getAddress() {
		return address;
	}


	public void setAddress(Address10 address) {
		this.address = address;
	}


	public String toString(){
		return address.toString();
	}
	
	public void init(){
		System.out.println("inside init");
	}
	
	public void destroy(){
		System.out.println("inside destroy");
	}
}

class Address10 {
	private String streetName;
	private String city;
	private int zip;
	
	public Address10(String streetName, String city, int zip){
		this.streetName=streetName;
		this.city=city;
		this.zip=zip;
	}
	
	public String toString(){
		return streetName + " : " + city + " : " + zip;
	}
	
	public void init(){
		System.out.println("inside init");
	}
	
	public void destroy(){
		System.out.println("inside destroy");
	}	
}

