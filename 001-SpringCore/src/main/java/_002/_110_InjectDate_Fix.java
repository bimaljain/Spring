/*
Declare a dateFormat bean, in “customer” bean, reference “dateFormat” bean as a factory bean. The factory method will call SimpleDateFormat.parse() 
to convert String into Date object automatically.
 */
package _002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _110_InjectDate_Fix {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_110_InjectDate_Fix.xml");
		Person109 person = (Person109)ctx.getBean("person110");
		System.out.println(person);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}
