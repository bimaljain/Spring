/*
-------------------------
Bean Definition Template:
-------------------------
1. You can create a Bean definition template which can be used by other child bean definitions. While defining a Bean Definition Template, you 
should not specify class attribute and should specify abstract attribute with a value of true.
2. The parent bean cannot be instantiated on its own because it is explicitly marked as abstract. When a definition is abstract like this, it is 
usable only as a pure template bean definition that serves as a parent definition for child definitions.

 */

package _002;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _103_AbstractBean {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_103_AbstractBean.xml");
		//We cannot instantiate abstract beans
//		Address baseAddress = (Address)ctx.getBean("baseAddress");
//		System.out.println(baseAddress);
		Address address = (Address)ctx.getBean("address");
		System.out.println(address);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}
