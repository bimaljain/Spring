package _002;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractApplicationContext;

public class _107_Import_inJavaConfig {
	public static void main(String[] args){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config107_Person.class);
		Person3 person = (Person3)ctx.getBean("person");
		System.out.println(person);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

@Configuration
@Import(Config107_Address.class) // you can import multiple config files, separated by commas
class Config107_Person{	
	@Bean
	public Person3 person(){
		return new Person3(107, "BJ", new Config107_Address().address());
	}
}

@Configuration
class Config107_Address{	
	@Bean
	public Address2 address(){
		return new Address2("Fremont St","Fremont",94538);
	}
}

