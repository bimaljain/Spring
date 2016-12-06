package org.core.contract.repositories;

import org.core.Employee;

public interface IEmployeeRepository {
	void persist(Employee employee);
}
