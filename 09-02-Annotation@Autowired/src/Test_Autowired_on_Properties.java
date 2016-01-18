import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.*;

public class Test_Autowired_on_Properties {
	public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      Autowired_on_Properties te = (Autowired_on_Properties) context.getBean("textEditor2");
      te.spellCheck();
   }
}