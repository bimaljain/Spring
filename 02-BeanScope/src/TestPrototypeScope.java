import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.HelloWorld;

public class TestPrototypeScope {
	public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

      HelloWorld objA = (HelloWorld) context.getBean("prototypescope");
      objA.setMessage("I am object A");
      objA.getMessage();
      
      HelloWorld objB = (HelloWorld) context.getBean("prototypescope");
      objB.getMessage();
   }
}