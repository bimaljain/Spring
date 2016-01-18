import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import com.spring.HelloWorld;

@SuppressWarnings("deprecation")
public class _01_Using_BeanFactoryContainer {
	public static void main(String[] args) {
		XmlBeanFactory factory = new XmlBeanFactory (new ClassPathResource("Beans.xml"));
		HelloWorld obj = (HelloWorld) factory.getBean("helloWorld");
		obj.getMessage();
	}
}