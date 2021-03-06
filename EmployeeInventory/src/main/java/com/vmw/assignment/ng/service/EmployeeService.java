package com.vmw.assignment.ng.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vmw.assignment.ng.constants.Constants;
import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;
import com.vmw.assignment.ng.executors.EmployeeTaskExecutor;
import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.RequestScopedParameter;
import com.vmw.assignment.ng.model.UploadEmployeeResponse;
import com.vmw.assignment.ng.model.dto.Employee;
import com.vmw.assignment.ng.model.request.UpdateEmployeeRequest;
import com.vmw.assignment.ng.repository.EmployeeRepository;
import com.vmw.assignment.ng.utils.RequestValidator;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RequestScopedParameter requestScopedParameter;

	@Autowired
	EmployeeTaskExecutor employeeTaskExecutor;
	
	@Autowired
	private RequestValidator requestValidator;

	public Employee findByIdOrName(Long id, String name) throws RequestValidationFailedException {

		validateFilters(id, name);

		if (id != null)
			return employeeRepository.findById(id).orElseThrow();

		return employeeRepository.findByName(name).orElseThrow();
	}

	public Employee save(EmployeeEntry employee) {
		return employeeRepository.save(new Employee(employee.getName(), employee.getAge()));
	}

	public Employee update(UpdateEmployeeRequest updateEmployeeRequest) throws RequestValidationFailedException {

		Long requestEmployeeId = updateEmployeeRequest.getId();
		Employee existingEmployee = employeeRepository.findById(requestEmployeeId).orElseThrow();

		boolean updateRequested = false;
		String requestedName = updateEmployeeRequest.getName();
		if (StringUtils.isNotBlank(requestedName)) {
			updateRequested = true;
			existingEmployee.setName(requestedName);
		}

		Integer requestedAge = updateEmployeeRequest.getAge();

		if (requestedAge != null) {
			requestValidator.validateAge(requestedAge);
			updateRequested = true;
			existingEmployee.setAge(requestedAge);
		}

		if (!updateRequested)
			return existingEmployee;

		return employeeRepository.save(existingEmployee);
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
	
	public ResponseEntity<String> delete(Long id, String name) throws RequestValidationFailedException {
		validateFilters(id, name);
		if (id != null) {
			employeeRepository.deleteById(id);
		} else {
			// To achive api behavior like above one, return 204 if data not found
			employeeRepository.findByName(name).orElseThrow();
			employeeRepository.deleteByName(name);
		}
		return ResponseEntity.ok().build();
	}

	private void validateFilters(Long id, String name) throws RequestValidationFailedException {
		boolean blankName = StringUtils.isBlank(name);
		if ((id == null && blankName) || (id != null && !blankName))
			throw new RequestValidationFailedException(Constants.REQUIRED_ONE_QUERY_PARAMETER_ID_OR_NAME);
	}

}
