package _001;

import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _007_MethodInjection_Replacer {
	public static void main(String[] args){
		ApplicationContext factory = new ClassPathXmlApplicationContext("_007_MethodInjection_Replacer.xml");
		for (int i=0;i<3;i++){
			PizzaShop pizzaShop = (PizzaShop)factory.getBean("pizzaShop");
			System.out.println(pizzaShop.getPizza());
		}
	}
}

class MethodReplacerImpl implements MethodReplacer{
	
	public MethodReplacerImpl() {
		System.out.println("Method Replacer is created");
	}

	public Object reimplement(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		return "Replacing the pizza";
	}
}

class PizzaShop {
	
	public PizzaShop(){
		System.out.println("PizzaShop is created");
	}
	
	public String getPizza(){
		return "Bought a pizza";
	}
}