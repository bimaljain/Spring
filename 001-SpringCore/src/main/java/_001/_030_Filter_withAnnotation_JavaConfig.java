package _001;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

public class _030_Filter_withAnnotation_JavaConfig {
	public static void main(String[] args){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(Config.class);
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

//uncomment this first
//@Configuration
//@ComponentScan(basePackages = "_001",
//     includeFilters = @Filter(MyAnnotation.class),
//     excludeFilters = @Filter(Repository.class))
class Config {

} 


