import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.HelloWorld;

public class TestDefaultScope {
	public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

      HelloWorld objA = (HelloWorld) context.getBean("defaultscope");
      objA.setMessage("I am object A");
      objA.getMessage();
      
      HelloWorld objB = (HelloWorld) context.getBean("defaultscope");
      objB.getMessage();
   }
}