/*

Setter-based DI
Setter-based DI is accomplished by the container calling setter methods on your beans after invoking a no-argument constructor or no-argument static 
factory method to instantiate your bean.

If you have many setter methods then it is convenient to use p-namespace in the XML configuration file.

Setter Dependency Injection(SDI) vs Constructor Dependency Injection(CDI)
The way we Inject
SDI usually happens after the object gets created by the container and by using the setter methods
Whereas CDI happens during the object creation by the container by passing dependencies as a parameter to the constructor.

Complete dependency injection vs Partial dependency injection
Since CDI happens during constructor execution, we need to pass all the necessary parameters to that constructor, so complete dependency injection is 
mandatory Whereas SDI happens after constructor gets executed , so we can do either partial dependency injection or complete dependency injection 
based on the requirement.

Readability
If we look at the readability of dependencies, we can prefer SDI, as in SDI we can see which property we are injecting based on the setter method 
and property tag.

Overriding injected value
If we use both CDI and SDI , Spring container will override the CDI by SDI.
It means , if we define both SDI and CDI on the same attributes, then SDI value will be injected to that attribute by spring container.

 */
package _001;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _003_SetterInjection_Collection {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_003_SetterInjection_Collection.xml");
		Person3 p = (Person3)ctx.getBean("person");
		System.out.println(p);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Person3 {
	private List<String> languages;
	private List<Address3> address;
	private List<Long> phone;
	
	private Map<String,String> QnA1;
	private Map<Long,Long> phone2;
	private Map<Question3,Answer3> QnA;
	
	private Set<String> skills;
	private Set<Long> phone3;
	private Set<Address3> address3;
	
	private String[] hobbies;
	private Long[] phone4;
	private Address3[] address2;
	
	private Properties languages2;
	private Properties phone5;
	private Properties  address5;

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

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public List<Address3> getAddress() {
		return address;
	}

	public void setAddress(List<Address3> address) {
		this.address = address;
	}

	public List<Long> getPhone() {
		return phone;
	}

	public void setPhone(List<Long> phone) {
		this.phone = phone;
	}

	public Map<String, String> getQnA1() {
		return QnA1;
	}

	public void setQnA1(Map<String, String> qnA1) {
		QnA1 = qnA1;
	}

	public Map<Long, Long> getPhone2() {
		return phone2;
	}

	public void setPhone2(Map<Long, Long> phone2) {
		this.phone2 = phone2;
	}

	public Map<Question3, Answer3> getQnA() {
		return QnA;
	}

	public void setQnA(Map<Question3, Answer3> qnA) {
		QnA = qnA;
	}

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	public Set<Long> getPhone3() {
		return phone3;
	}

	public void setPhone3(Set<Long> phone3) {
		this.phone3 = phone3;
	}

	public Set<Address3> getAddress3() {
		return address3;
	}

	public void setAddress3(Set<Address3> address3) {
		this.address3 = address3;
	}

	public String[] getHobbies() {
		return hobbies;
	}

	public void setHobbies(String[] hobbies) {
		this.hobbies = hobbies;
	}

	public Long[] getPhone4() {
		return phone4;
	}

	public void setPhone4(Long[] phone4) {
		this.phone4 = phone4;
	}

	public Address3[] getAddress2() {
		return address2;
	}

	public void setAddress2(Address3[] address2) {
		this.address2 = address2;
	}

	public Properties getLanguages2() {
		return languages2;
	}

	public void setLanguages2(Properties languages2) {
		this.languages2 = languages2;
	}

	public Properties getPhone5() {
		return phone5;
	}

	public void setPhone5(Properties phone5) {
		this.phone5 = phone5;
	}

	public Properties getAddress5() {
		return address5;
	}

	public void setAddress5(Properties address5) {
		this.address5 = address5;
	}
}


class Address3 {
	private String streetName;
	private String city;
	private int zip;
	
	public Address3(String streetName, String city, int zip){
		this.streetName=streetName;
		this.city=city;
		this.zip=zip;
	}
	
	public String toString(){
		return streetName + " : " + city + " : " + zip;
	}	
}

class Question3 {
	private int id;
	private String question;
	
	public Question3(int id, String question){
		this.id=id;
		this.question=question;
	}
	
	public String toString(){
		return id + " : " + question;
	}
}

class Answer3 {
	private int id;
	private String answer;
	
	public Answer3(int id, String answer){
		this.id=id;
		this.answer=answer;
	}
	
	public String toString(){
		return id + " : " + answer;
	}
}
