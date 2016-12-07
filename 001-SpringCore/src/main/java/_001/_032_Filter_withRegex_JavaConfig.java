package _001;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;

public class _032_Filter_withRegex_JavaConfig {
	public static void main(String[] args){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(Config3.class);
		ctx.refresh();
		
		try{
			Service a=(Service)ctx.getBean(Service.class);
			a.doSomethingService();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
		
		try{
			Util b=(Util)ctx.getBean(Util.class);
			b.doSomethingUtil();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
		
		try{
			DAO c=(DAO)ctx.getBean(DAO.class);
			c.doSomethingDAO();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
	}

}

//@Configuration
//@ComponentScan(basePackages = "_001",
//     includeFilters = @Filter(type = FilterType.REGEX, pattern="_001.*.Service3"),
//     excludeFilters = @Filter(type = FilterType.REGEX, pattern ="_001.*.Util3"))
class Config3 {

} 