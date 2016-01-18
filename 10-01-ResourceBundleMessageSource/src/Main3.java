import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main3 {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans3.xml");
		org.spring.TextEditor3 te = (org.spring.TextEditor3) context.getBean("textEditor3");
		te.messages();
	}
}