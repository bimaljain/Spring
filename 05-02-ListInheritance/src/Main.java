import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.HelloIndia;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		HelloIndia objA = (HelloIndia) context.getBean("helloIndia");
		for (String msg: objA.getMessages()){
			System.out.println(msg);
		}
		
		System.out.println("+++++++++++");
		HelloIndia objB = (HelloIndia) context.getBean("helloAssam1");
		for (String msg: objB.getMessages()){
			System.out.println(msg);
		}
		
		System.out.println("+++++++++++");
		HelloIndia objC = (HelloIndia) context.getBean("helloAssam2");
		for (String msg: objC.getMessages()){
			System.out.println(msg);
		}
	}
}