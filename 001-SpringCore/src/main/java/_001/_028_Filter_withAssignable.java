package _001;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _028_Filter_withAssignable {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_028_Filter_withAssignable.xml");
		
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

interface IService {
	public void doSomethingService();
}

interface IUtil {
	public void doSomethingUtil();
}

interface IDAO {
	public void doSomethingDAO();
}

class Service2 implements IService{
	public void doSomethingService(){
		System.out.println("Service is doing something");
	}
}

class Util2 implements IUtil{
	public void doSomethingUtil(){
		System.out.println("Util is doing something");
	}
}

class DAO2 implements IDAO{
	public void doSomethingDAO(){
		System.out.println("DAO is doing something");
	}

}
