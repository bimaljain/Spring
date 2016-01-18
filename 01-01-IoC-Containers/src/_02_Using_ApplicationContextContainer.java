import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.spring.HelloWorld;

public class _02_Using_ApplicationContextContainer {
	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext
				("C:\\_Bimal\\DevelopmentKit\\_Workspaces\\SpringFramework3.1_Workspace\\01-IoC-Containers\\src\\Beans.xml");
		HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
		obj.getMessage();
	}
}