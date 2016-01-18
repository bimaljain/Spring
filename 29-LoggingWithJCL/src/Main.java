import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.HelloWorld;
import org.apache.commons.logging. Log;
import org.apache.commons.logging. LogFactory;

public class Main {
   static Log log = LogFactory.getLog(Main.class.getName());
   public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      log.info(">>>>>Going to create HelloWord Obj");
      HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
      obj.getMessage();
      log.info(">>>>>Exiting the program");
   }
}