/*
@Autowired with (required=false): 
By default, the @Autowired annotation implies the dependency is required similar to @Required annotation, however, you can turn off the default 
behavior by using (required=false) option with @Autowired.
 */
package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class _025_AutowiredWithoutRequired {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_013_BeanLifeCycle_Annotation.xml");
		Student3 student = (Student3)ctx.getBean("student3");
		System.out.println(student);
		
	}
}

//@Component //uncomment this first
class Student3 {
	
	@Autowired(required=false) // age is not configured but this will not throw any exception
	private Integer age;
	
	@Autowired
	@Value("Bimal") // required=true. If you don't supply a string, autowiring will fail.
	private String name;

	public String toString(){
		return "Result: " + name + " " + age;
	}
}


