/*
@Required: The @Required annotation applies to bean property setter methods and it indicates that the affected bean property must be populated 
in XML configuration file at configuration time otherwise the container throws a BeanInitializationException exception. If you want to make sure 
that all the dependencies are covered and you don’t get any NPE after the application is deployed, you can use @Required annotation. When the 
application is initialized, Spring will throw an exception if it cannot find <property> tag for the REQUIRED member variable. Spring will not 
proceed with the execution of the application. This way we can catch the exception upfront and fix it. Without Required annotation, NPE exception 
will be thrown at runtime.
 */

package _001;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _024_Required_Fix {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_024_Required_Fix.xml");
		Student2 student = (Student2)ctx.getBean("student");
		System.out.println(student);
		
	}
}

class Student2 {
	private Integer age;
	private String name;

	@Required
	public void setAge(Integer age) { this.age = age; }
	public Integer getAge() { return age; }

	@Required
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }

	public String toString(){
		return name + " " + age;
	}
}

/*
OUTPUT:
null 50
*/
