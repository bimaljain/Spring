import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main1 {
	public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans1.xml");
      System.out.println(context.getMessage("greeting", null, "Default greeting", null));
      System.out.println(context.getMessage("greeting1", null, "Default greeting", null));
      System.out.println(context.getMessage("greeting2", null, "Default greeting", null));
   }
}