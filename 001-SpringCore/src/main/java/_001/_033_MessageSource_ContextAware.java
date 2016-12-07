package _001;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _033_MessageSource_ContextAware implements ApplicationContextAware{
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_033_MessageSource.xml");
		_033_MessageSource_ContextAware x =(_033_MessageSource_ContextAware)ctx.getBean("contextBean");
		x.doSomething();
		System.out.println("\n");
	}

	ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;		
	}
	
	public void doSomething(){
		System.out.println(applicationContext.getMessage("greeting", null, "Hello Bimal", null));
		System.out.println(applicationContext.getMessage("family.greeting", new String[]{"Bimal", "Bharat"}, "Hello Bimal", null));
		System.out.println(applicationContext.getMessage("country.greeting", null, "Default greeting", Locale.US));
		System.out.println(applicationContext.getMessage("country.greeting", null, "Default greeting", Locale.UK));
	}
}
