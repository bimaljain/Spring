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
	//Setter DI only need to know about setter methods. (and not the property names)
	//below, properties has a different names than their setter methods. But setter DI still works.
	private List<String> languages1;
	private List<Address3> address1;
	private List<Long> phone1;
	
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
		return  "LIST>" + languages1 + " \n " 
				+ address1 + " \n " 
				+ phone1 + " \n " 
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
		return languages1;
	}

	public void setLanguages(List<String> languages) {
		this.languages1 = languages;
	}

	public List<Address3> getAddress() {
		return address1;
	}

	public void setAddress(List<Address3> address) {
		this.address1 = address;
	}

	public List<Long> getPhone() {
		return phone1;
	}

	public void setPhone(List<Long> phone) {
		this.phone1 = phone;
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
