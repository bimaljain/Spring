package org.repository;

import org.core.Employee;
import org.core.contract.repositories.IEmployeeRepository;

public class EmployeeRepository implements IEmployeeRepository{

	public void persist(Employee employee) {
		System.out.println("CREATED OR UPDATED: " + employee);		
	}
}
