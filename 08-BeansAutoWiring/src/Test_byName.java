import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.*;

public class Test_byName {
	public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans_byName.xml");
      TextEditor te = (TextEditor) context.getBean("textEditor");
      te.getName();
      te.spellCheck();
   }
}