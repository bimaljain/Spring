import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.CustomEventPublisher;

public class Main {
   public static void main(String[] args) {
      ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      CustomEventPublisher cvp = (CustomEventPublisher) context.getBean("customEventPublisher");
      cvp.publish();  
      cvp.publish();
   }
}