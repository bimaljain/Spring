import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.*;

public class Main {
	public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      TextEditor te1 = (TextEditor) context.getBean("textEditor1");
      System.out.println("textEditor1");
      te1.spellCheck();
      TextEditor te2 = (TextEditor) context.getBean("textEditor2");
      System.out.println("textEditor2");
      te2.spellCheck();
      TextEditor te3 = (TextEditor) context.getBean("textEditor3");
      System.out.println("textEditor3");
      te3.spellCheck();
   }
}