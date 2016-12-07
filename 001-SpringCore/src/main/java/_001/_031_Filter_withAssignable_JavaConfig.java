package _001;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class _031_Filter_withAssignable_JavaConfig {
	public static void main(String[] args){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Config2.class);
		
		try{
			Service2 a=(Service2)ctx.getBean(Service2.class);
			a.doSomethingService();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
		
		try{
			Util2 b=(Util2)ctx.getBean(Util2.class);
			b.doSomethingUtil();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
		
		try{
			DAO2 c=(DAO2)ctx.getBean(DAO2.class);
			c.doSomethingDAO();
		}catch(BeansException ex){
			System.out.println(ex.getMessage());
		}
	}

}

//uncomment this first
//@Configuration
//@ComponentScan(basePackages = "_001",
//     includeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes =IService.class),
//     excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes =IUtil.class))
class Config2 {

} 
