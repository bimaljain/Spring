package org.core.contract.usecases;

import org.core.Employee;
import org.core.contract.repositories.IEmployeeRepository;

public interface ICreateOrUpdateEmployee {
	void createOrUpdateEmployee(Employee emp);
}
