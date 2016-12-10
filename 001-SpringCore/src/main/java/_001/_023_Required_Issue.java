/*
One difference between setter vs constructor injection in Spring and one of the drawback of setter injection is that it does not ensures 
dependency Injection. You can not guarantee that certain dependency is injected or not, which means you may have an object with incomplete 
dependency. On other hand constructor Injection does not allow you to construct object, until your dependencies are ready.
[Remember that @Autowired on setter method means @Required by default. So above is needed only when we do non-annotated setter DI.]

Check below how one may create an incomplete bean
 */

package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _023_Required_Issue {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_023_Required_Issue.xml");
		Student student = (Student)ctx.getBean("student");
		System.out.println(student);
		
	}
}

class Student {
	private Integer age;
	private String name;

	public void setAge(Integer age) { this.age = age; }
	public Integer getAge() { return age; }

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
