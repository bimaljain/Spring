package org.main;

import org.core.Employee;
import org.core.contract.usecases.ICreateOrUpdateEmployee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.usecase.CreateOrUpdateEmployee;

public class Main {

	public static void main(String[] args){
		// Don't do this. Use spring instead
		//		IEmployeeRepository employeeRepository = new EmployeeRepository();
		//		ICreateOrUpdateEmployee createOrUpdateEmployee = new CreateOrUpdateEmployee(employeeRepository);
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		ICreateOrUpdateEmployee createOrUpdateEmployee = (CreateOrUpdateEmployee) context.getBean("createOrUpdateEmployee");
		Employee emp = new Employee();
		emp.setFirstName("Meghna"); emp.setLastName("Jain"); emp.setDesignation("Associate");
		createOrUpdateEmployee.createOrUpdateEmployee(emp);
	}
}
