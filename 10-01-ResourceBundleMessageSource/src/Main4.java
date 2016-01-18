import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main4 {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans4.xml");
		org.spring.TextEditor4 te = (org.spring.TextEditor4) context.getBean("textEditor4");
		te.messages();
	}
}