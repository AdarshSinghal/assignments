package com.vmw.assignment.ng.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.RequestScopedParameter;
import com.vmw.assignment.ng.model.dto.Employee;
import com.vmw.assignment.ng.repository.EmployeeRepository;

/**
 * Responsible for creating empoyees.
 * 
 * @author adarsh
 *
 */
@Component
public class EmployeeFactory {

	@Autowired
	RequestScopedParameter requestScopedParameter;

	@Autowired
	EmployeeRepository employeeRepository;

	/**
	 * Create employee records from the provided details in request. Identify the
	 * employees who are not present in database and create them.
	 * 
	 * @param employees
	 */
	public void createEmployeeIfNotPresent(List<EmployeeEntry> employees) {
		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.CHECK_EMP_EXISTS);
		List<String> employeeNames = employees.stream().map((e) -> e.getName()).collect(Collectors.toList());
		List<Employee> existingEmployees = employeeRepository.findAllByNameIn(employeeNames);

		List<Employee> newEmployees = new ArrayList<>();

		for (EmployeeEntry employee : employees) {
			boolean isExistingEmployee = false;
			for (Employee existingEmployee : existingEmployees) {
				if (employee.getName().equals(existingEmployee.getName())) {
					isExistingEmployee = true;
				}
			}
			if (!isExistingEmployee) {
				newEmployees.add(new Employee(employee.getName(), employee.getAge()));
			}
		}

		if (!newEmployees.isEmpty()) {
			requestScopedParameter.saveTaskStatus(CurrentTaskStatus.NEW_EMP_DETECTED);
			// Add action here before creating employees on NEW_EMP_DETECTED event.
			requestScopedParameter.saveTaskStatus(CurrentTaskStatus.CREATING_EMPLOYEES);
			employeeRepository.saveAll(newEmployees);
			requestScopedParameter.saveTaskStatus(CurrentTaskStatus.CREATED_EMPLOYEES);
		} else {
			requestScopedParameter.saveTaskStatus(CurrentTaskStatus.SKIPPING_EMP_CREATION);

		}
	}

}
