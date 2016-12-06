package _001;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _011_CircularDependency_Fix {

	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_011_CircularDependency_Fix.xml");
		A a= (A)ctx.getBean("beanA");
		B b= (B)ctx.getBean("beanB");
		System.out.println(a.getB() + " : " + b.getA());
	}
}
