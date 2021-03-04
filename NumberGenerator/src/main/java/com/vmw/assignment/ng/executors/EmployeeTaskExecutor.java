package com.vmw.assignment.ng.executors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;
import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.RequestScopedParameter;

@Service
public class EmployeeTaskExecutor {

	@Autowired
	private RecordsValidator recordsValidator;
	@Autowired
	private EmployeeFactory employeeFactory;
	@Autowired
	private AgeAnalyzer ageAnalyzer;

	@Autowired
	RequestScopedParameter requestScopedParameter;

	public void execute(List<EmployeeEntry> employees) throws RequestValidationFailedException {
		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.VALIDATION_STARTS);
		recordsValidator.validate(employees);
		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.VALIDATION_ENDS);

		employeeFactory.createEmployeeIfNotPresent(employees);

		ageAnalyzer.performAgeAnalysis(employees);

		// Anything like consolidation to do after age analysis task.
		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.COMPLETED);
	}

}
