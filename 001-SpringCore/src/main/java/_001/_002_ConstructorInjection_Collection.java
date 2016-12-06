/*
Injecting Collection
You have seen how to configure primitive data type using value attribute and object references using ref attribute of the <property> tag in your 
Bean configuration file. Both the cases deal with passing singular value to a bean. We can also pass plural values like Java Collection types List, 
Set, Map, and Properties. To handle the situation, Spring offers five types of collection configuration elements which are as follows:
 
Element	Description:
<list>	This helps in wiring a list of values, allowing duplicates.
<set>	This helps in wiring a set of values but without any duplicates.
<map>	This can be used to inject a collection of name-value pairs where name and value can be of any type.
<props>	This can be used to inject a collection of name-value pairs where the name and value are both Strings.
<array>	

You will come across two situations (a) Passing direct values of the collection and (b) Passing a reference of a bean as one of the collection 
elements.
 */

package _001;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _002_ConstructorInjection_Collection {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_002_ConstructorInjection_Collection.xml");
		Person2 p = (Person2)ctx.getBean("person");
		System.out.println(p);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Person2 {
	private List<String> languages;
	private List<Address2> address;
	private List<Long> phone;
	
	private Map<String,String> QnA1;
	private Map<Long,Long> phone2;
	private Map<Question,Answer> QnA;
	
	private Set<String> skills;
	private Set<Long> phone3;
	private Set<Address2> address3;
	
	private String[] hobbies;
	private Long[] phone4;
	private Address2[] address2;
	
	private Properties languages2;
	private Properties phone5;
	private Properties  address5;

	public Person2(List<String> languages, List<Long> phoneNumber, List<Address2> address,  
			Map<String,String> QnA1, Map<Long,Long> phone2, Map<Question,Answer> QnA,  
			Set<String> skills, Set<Long> phone3, Set<Address2> address3,
			String[] hobbies, Long[] phone4, Address2[] address2,
			Properties languages2, Properties phone5, Properties  address5)
	
	{
		
		this.languages=languages;	
		this.address=address;
		this.phone=phoneNumber;
		this.QnA1=QnA1;
		this.phone2=phone2;
		this.QnA=QnA;
		this.skills=skills;
		this.phone3=phone3;
		this.address3=address3;
		this.hobbies=hobbies;
		this.phone4=phone4;
		this.address2=address2;
		this.languages2=languages2;
		this.phone5=phone5;
		this.address5=address5;
	}
	
	public String toString(){
		return  "LIST>" + languages + " \n " 
				+ address + " \n " 
				+ phone + " \n " 
				+"MAP>"+ QnA1 + " \n " 
				+ phone2 + "\n"
				+ QnA  + " \n " 
				+"SET>"+ skills + " \n " 
				+ phone3 + "\n"
				+ address3 + " \n " 
				+"ARRAY>"+ hobbies[0] + " : " + hobbies[1] + " \n "
				+ phone4[0] + " : " + phone4[1] + "\n"
				+ address2[0] + " : " + address2[1] + "\n"
				+"PROP>"+ languages2 + "\n"		
				+ phone5 + "\n"
				+ address5
				;
	}
}

class Address2 {
	private String streetName;
	private String city;
	private int zip;
	
	public Address2(String streetName, String city, int zip){
		this.streetName=streetName;
		this.city=city;
		this.zip=zip;
	}
	
	public String toString(){
		return streetName + " : " + city + " : " + zip;
	}	
}

class Question {
	private int id;
	private String question;
	
	public Question(int id, String question){
		this.id=id;
		this.question=question;
	}
	
	public String toString(){
		return id + " : " + question;
	}
}

class Answer {
	private int id;
	private String answer;
	
	public Answer(int id, String answer){
		this.id=id;
		this.answer=answer;
	}
	
	public String toString(){
		return id + " : " + answer;
	}
}

