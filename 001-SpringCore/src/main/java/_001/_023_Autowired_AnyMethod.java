/*
Autowiring can be done on any method where it searches for the type of the bean specified in the method’s parameter.
So type of parameter of a method should be available inside beans.xml
 */
package _001;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component //uncomment this first
public class _023_Autowired_AnyMethod {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_013_BeanLifeCycle_Annotation.xml");
		System.out.println((_023_Autowired_AnyMethod)ctx.getBean("_023_Autowired_AnyMethod"));
	}

	private Address9 address99;
	
	//Autowiring on any method need a default constructor, otherwise you get below error
	//Failed to instantiate: No default constructor found
	//If you dont specify any constructor, you always get default constructor. I am still writing one below.
	public _023_Autowired_AnyMethod(){
	}
	
	//@Autowired fails by Type since there are 2 beans of Address9 type. Next it checks for qualifier, but there is none. So finally it autowires 
	//byName (spring bean with name address2). We don't care about the class property name.
	@Autowired
	public void setCustomAddress(Address9 address2) {
		this.address99 = address2;
	}

	public String toString(){
		return address99.toString();
	}
}

/*
CONCLUSION:
1.) Constructor DI: looks at the constructor's parameter list
2.) Setter DI: looks at the names of ALL setter methods
3.) Autowiring byName: looks at the names of ALL setter methods
4.) Autowiring byType: looks at the type of argument in the setter args list
5.) Autowiring byConstructor: looks at the constructor's arg list. First check byType and later byName
6.) JavaConfig: @Bean create bean with method name.
7.) Autowired Property: byType --> Qualifier --> byName of the property
8.) Autowired Constructor: byType --> Qualifier --> byName of constructor args list
9.) Autowired Setter: byType --> Qualifier --> byName  of setter args list
10.) Autowired on any method: byType --> Qualifier --> byName  of method args list
 */
