import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.*;

public class Test_Autowired_on_SetterMethod {
	public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      Autowired_on_SetterMethod te = (Autowired_on_SetterMethod) context.getBean("textEditor1");
      te.spellCheck();
   }
}