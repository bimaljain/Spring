package _001;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _035_MessageSource_InjectMessageSource{
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_033_MessageSource.xml");
		
		_035_MessageSource_InjectMessageSource z =(_035_MessageSource_InjectMessageSource)ctx.getBean("injectBean");
		z.doSomething();
		System.out.println("\n");
	}

	MessageSource messageSource;
	
	public _035_MessageSource_InjectMessageSource(MessageSource messageSource){
		this.messageSource=messageSource;
	}

	public void doSomething(){
		System.out.println(messageSource.getMessage("greeting", null, "Hello Bimal", null));
		System.out.println(messageSource.getMessage("family.greeting", new String[]{"Bimal", "Bharat"}, "Hello Bimal", null));
		System.out.println(messageSource.getMessage("country.greeting", null, "Default greeting", Locale.UK));
		System.out.println(messageSource.getMessage("country.greeting", null, "Default greeting", Locale.US));
		
	}

}
