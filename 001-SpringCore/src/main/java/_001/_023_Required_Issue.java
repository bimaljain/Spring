/*
Setter vs Constructor DI:
1. Partial dependency: can be injected using setter injection but it is not possible by constructor. Suppose there are 3 properties in a class, having 
3 arg constructor and setters methods. In such case, if you want to pass information for only one property, it is possible by setter method only.
2. Overriding: Setter injection overrides the constructor injection. If we use both constructor and setter injection, IOC container will use the 
setter injection.
3. Changes: We can easily change the value by setter injection. It doesn't create a new bean instance always like constructor. So setter injection 
is flexible than constructor injection

One difference between setter vs constructor injection in Spring and one of the drawback of  setter injection is that it does not ensures 
dependency Injection. You can not guarantee that certain dependency is injected or not, which means you may have an object with incomplete 
dependency. On other hand constructor Injection does not allow you to construct object, until your dependencies are ready.

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
