package com.vmw.assignment.ng.executors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;
import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.RequestScopedParameter;

/**
 * Responsible for validation of records
 * 
 * @author adarsh
 *
 */
@Component
public class RecordsValidator {

	@Autowired
	RequestScopedParameter requestScopedParameter;

	/**
	 * Iterates all the employees and perform validation on each record and filter
	 * out the malformed records.
	 * 
	 * @param employees
	 * @throws RequestValidationFailedException
	 */
	public void validate(List<EmployeeEntry> employees) throws RequestValidationFailedException {

		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.VALIDATION_STARTS);

		List<EmployeeEntry> validAgeGroupEmployees = employees.stream()
				.filter((e) -> e.getAge() > 20 && e.getAge() < 100).collect(Collectors.toList());
		// If there is no employee with valid age, then stop further processing.
		if (validAgeGroupEmployees.isEmpty())
			throw new RequestValidationFailedException();

		employees = validAgeGroupEmployees;

		// Execution of this line means validation passed.
		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.VALIDATION_ENDS);

	}

}
