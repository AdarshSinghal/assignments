package com.vmw.assignment.ng.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vmw.assignment.ng.constants.Constants;
import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;
import com.vmw.assignment.ng.executors.EmployeeTaskExecutor;
import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.RequestScopedParameter;
import com.vmw.assignment.ng.model.entity.Employee;
import com.vmw.assignment.ng.model.request.UpdateEmployeeRequest;
import com.vmw.assignment.ng.model.response.UploadEmployeeResponse;
import com.vmw.assignment.ng.repository.EmployeeRepository;
import com.vmw.assignment.ng.utils.CsvReader;
import com.vmw.assignment.ng.utils.CsvWriter;
import com.vmw.assignment.ng.utils.RequestValidator;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RequestScopedParameter requestScopedParameter;

	@Autowired
	private EmployeeTaskExecutor employeeTaskExecutor;

	@Autowired
	private CsvWriter csvWriter;
	@Autowired
	private CsvReader csvReader;
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

	public void process(List<EmployeeEntry> employees) throws RequestValidationFailedException {

		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.SUBMITTED);
		employeeTaskExecutor.execute(employees);
	}

	public ResponseEntity<UploadEmployeeResponse> upload(MultipartFile file)
			throws IOException, RequestValidationFailedException {
		String uuid = UUID.randomUUID().toString();
		List<EmployeeEntry> employees = csvReader.readFromFile(file);
		requestScopedParameter.setEmployees(employees);
		requestScopedParameter.setTaskId(uuid);
		process(employees);
		UploadEmployeeResponse response = new UploadEmployeeResponse();
		response.setTaskId(uuid);
		response.setStatus(requestScopedParameter.getTaskStatusList()
				.get(requestScopedParameter.getTaskStatusList().size() - 1).getStatus());

		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Resource> download(List<String> employees)
			throws IOException, RequestValidationFailedException {

		requestValidator.validateDataForCSVWrite(employees);

		File file = csvWriter.writeValuesToFile(employees);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		respHeaders.setContentLength(file.length());
		respHeaders.setContentDispositionFormData(Constants.ATTACHMENT, Constants.EMP_CSV);
		return ResponseEntity.ok().headers(respHeaders).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
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
