import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main2 {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans2.xml");
		org.spring.TextEditor2 te = (org.spring.TextEditor2) context.getBean("textEditor2");
		te.messages();
	}
}