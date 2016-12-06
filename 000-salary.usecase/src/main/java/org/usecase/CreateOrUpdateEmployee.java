package org.usecase;

import org.core.Employee;
import org.core.contract.repositories.IEmployeeRepository;
import org.core.contract.usecases.ICreateOrUpdateEmployee;

public class CreateOrUpdateEmployee implements ICreateOrUpdateEmployee{
	
	private IEmployeeRepository employeeRepository;
	
	public CreateOrUpdateEmployee(IEmployeeRepository employeeRepository){
		this.employeeRepository = employeeRepository;
	}

	public void createOrUpdateEmployee(Employee emp) {
		System.out.println("BUSINESS LOGIC IN PLAY");
		System.out.println("PERSISTING DATA");
		employeeRepository.persist(emp);		
	}

}
