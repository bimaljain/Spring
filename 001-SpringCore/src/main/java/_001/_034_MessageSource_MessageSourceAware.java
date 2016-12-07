package _001;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _034_MessageSource_MessageSourceAware implements MessageSourceAware{
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_033_MessageSource.xml");
		_034_MessageSource_MessageSourceAware y =(_034_MessageSource_MessageSourceAware)ctx.getBean("MsgSourceBean");
		y.doSomething();
	}
	MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource=messageSource;	
	}
	
	public void doSomething(){
		System.out.println(messageSource.getMessage("greeting", null, "Hello Bimal", null));
		System.out.println(messageSource.getMessage("family.greeting", new String[]{"Bimal", "Bharat"}, "Hello Bimal", null));
		System.out.println(messageSource.getMessage("country.greeting", null, "Default greeting", Locale.UK));
		System.out.println(messageSource.getMessage("country.greeting", null, "Default greeting", Locale.US));
		
	}

}
