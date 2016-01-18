import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.HelloWorld;

public class TestSingletonScope {
	public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

      HelloWorld objA = (HelloWorld) context.getBean("singletonscope");
      objA.setMessage("I am object A");
      objA.getMessage();
      
      HelloWorld objB = (HelloWorld) context.getBean("singletonscope");
      objB.getMessage();
   }
}