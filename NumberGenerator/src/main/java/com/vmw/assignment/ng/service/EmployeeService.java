package com.vmw.assignment.ng.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;
import com.vmw.assignment.ng.executors.EmployeeTaskExecutor;
import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.RequestScopedParameter;
import com.vmw.assignment.ng.model.UploadEmployeeResponse;
import com.vmw.assignment.ng.model.dto.Employee;
import com.vmw.assignment.ng.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RequestScopedParameter requestScopedParameter;

	@Autowired
	EmployeeTaskExecutor employeeTaskExecutor;

	public Employee findById(Long id) {
		return employeeRepository.findById(id).get();
	}

	public Employee save(EmployeeEntry employee) {
		return employeeRepository.save(new Employee(employee.getName(), employee.getAge()));
	}

	public ResponseEntity<UploadEmployeeResponse> process(List<EmployeeEntry> employees)
			throws RequestValidationFailedException {
		String uuid = UUID.randomUUID().toString();
		requestScopedParameter.setTaskId(uuid);
		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.SUBMITTED);
		employeeTaskExecutor.execute(employees);
		UploadEmployeeResponse response = new UploadEmployeeResponse();
		response.setTaskId(uuid);
		response.setStatus(CurrentTaskStatus.SUBMITTED.toString());

		return ResponseEntity.ok(response);
	}

}
