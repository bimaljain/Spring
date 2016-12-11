/*
Below example will throw exception:
cannot convert value of type [java.lang.String] to required type [java.util.Date] for property 'date'
 */
package _002;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _109_InjectDate_Issue {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_109_InjectDate_Issue.xml");
		Person109 person = (Person109)ctx.getBean("person109");
		System.out.println(person);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Person109 {
	Date date;

	public Person109(){
	}	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String toString(){
		return date +"";
	}
}

