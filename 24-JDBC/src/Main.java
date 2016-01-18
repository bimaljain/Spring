import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.*;

public class Main {
   public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      StudentDAOImpl StudentDAOImpl = (StudentDAOImpl)context.getBean("studentDAOImpl");
      
      System.out.println("------Records Creation--------" );
      StudentDAOImpl.create("Zara", 11);
      StudentDAOImpl.create("Nuha", 2);
      StudentDAOImpl.create("Ayan", 15);

      System.out.println("------Listing Multiple Records--------" );
      List<Student> students = StudentDAOImpl.listStudents();
      for (Student record : students) {
         System.out.print("ID : " + record.getId() );
         System.out.print(", Name : " + record.getName() );
         System.out.println(", Age : " + record.getAge()); }

      System.out.println("----Updating Record with ID = 2 -----" );
      StudentDAOImpl.update(2, 20);

      System.out.println("----Listing Record with ID = 2 -----" );
      Student student = StudentDAOImpl.getStudent(2);
      System.out.print("ID : " + student.getId() );
      System.out.print(", Name : " + student.getName() );
      System.out.println(", Age : " + student.getAge());
   }
}