package _001;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _029_Filter_withRegex {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_029_Filter_withRegex.xml");
		
		try{
			Service3 a=(Service3)ctx.getBean(Service3.class);
			a.doSomethingService();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
		
		try{
			Util3 b=(Util3)ctx.getBean(Util3.class);
			b.doSomethingUtil();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
		
		try{
			DAO3 c=(DAO3)ctx.getBean(DAO3.class);
			c.doSomethingDAO();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
	}

}

class DAO3 {
	public void doSomethingDAO(){
		System.out.println("DAO is doing something");
	}
}

class Service3 {
	public void doSomethingService(){
		System.out.println("Service is doing something");
	}
}

class Util3 {
	public void doSomethingUtil(){
		System.out.println("Util is doing something");
	}
}
