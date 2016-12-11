/*
The ListFactoryBean class provides developer a way to create a concrete List collection class (ArrayList and LinkedList) in Spring’s bean 
configuration file.

Here’s a ListFactoryBean example, it will instantiate an ArrayList at runtime, and inject it into a bean property.
 */

package _002;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _108_ConcreteCollectionBeans {
	public static void main(String[] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_108_ConcreteCollectionBeans.xml");
		Person108 person = (Person108)ctx.getBean("person");
		System.out.println(person);
		((AbstractApplicationContext)ctx).registerShutdownHook();
	}
}

class Person108 {
	private List<Address> listAddress;
	private Set<Address> address;
	private Map<String, Address> mapAddress;

	public Person108(){
	}

	public String toString(){
		return listAddress + " : " + address + " : " + mapAddress ;
	}

	public List<Address> getListAddress() {
		return listAddress;
	}

	public void setListAddress(List<Address> listAddress) {
		this.listAddress = listAddress;
	}

	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> addressSet) {
		this.address = addressSet;
	}

	public Map<String, Address> getMapAddress() {
		return mapAddress;
	}

	public void setMapAddress(Map<String, Address> mapAddress) {
		this.mapAddress = mapAddress;
	}
	
}
