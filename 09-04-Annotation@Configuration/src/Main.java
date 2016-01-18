import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.spring.HelloWorld;

public class Main {
	public static void main(String[] args) {
		   ApplicationContext ctx = new AnnotationConfigApplicationContext(com.spring.HelloWorldConfig.class);
		   HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
		   helloWorld.setMessage("Hello World!");
		   helloWorld.getMessage();
		   ((AbstractApplicationContext) ctx).registerShutdownHook();
		}
}