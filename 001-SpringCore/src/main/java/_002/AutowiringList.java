package _002;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class AutowiringList {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("AutowiringList.xml");
		ListInjection x = (ListInjection)ctx.getBean("listInjection");
		List<Car> list = x.getCars();
		for (Car car:list){
			car.getCar();
		}
	}
}

@Component
class ListInjection {

	@Autowired
	private List<Car> cars;

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
}


interface Car {
	public void getCar();
}

@Component
class Toyota implements Car {
	public void getCar(){
		System.out.println("My Toyota");
	}
}

@Component
class Volkswagen implements Car {
	public void getCar(){
		System.out.println("My Volkswagen");
	}

}
