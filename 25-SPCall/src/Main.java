import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.*;

public class Main {
   public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      StudentDAOImpl StudentDAOImpl = (StudentDAOImpl)context.getBean("studentDAOImpl");
      System.out.println("----Listing Record with ID = 2 -----" );
      Student student = StudentDAOImpl.getStudent(2);
      System.out.print("ID : " + student.getId() );
      System.out.print(", Name : " + student.getName() );
      System.out.println(", Age : " + student.getAge());
   }
}