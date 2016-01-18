import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.*;

public class Test_Autowired_on_Constructors {
	public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      Autowired_on_Constructors te = (Autowired_on_Constructors) context.getBean("textEditor3");
      te.spellCheck();
   }
}