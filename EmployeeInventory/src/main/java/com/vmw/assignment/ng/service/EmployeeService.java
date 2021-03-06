package com.vmw.assignment.ng.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.assignment.ng.constants.Constants;
import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;
import com.vmw.assignment.ng.executors.EmployeeTaskExecutor;
import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.RequestScopedParameter;
import com.vmw.assignment.ng.model.UploadEmployeeResponse;
import com.vmw.assignment.ng.model.dto.Employee;
import com.vmw.assignment.ng.model.request.TaskRequest;
import com.vmw.assignment.ng.model.request.UpdateEmployeeRequest;
import com.vmw.assignment.ng.repository.EmployeeRepository;
import com.vmw.assignment.ng.utils.CsvReader;
import com.vmw.assignment.ng.utils.CsvWriter;
import com.vmw.assignment.ng.utils.RequestValidator;

/**
 * Business logics for Employee operations are maintained in this class.
 * 
 * @author adarsh
 *
 */
@Service
public class EmployeeService {

	private static final String EMPLOYEE_S_CSV = "Employee_%s.csv";

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RequestScopedParameter requestScopedParameter;

	@Autowired
	EmployeeTaskExecutor employeeTaskExecutor;

	@Autowired
	private RequestValidator requestValidator;

	@Autowired
	private CsvWriter csvWriter;
	@Autowired
	private CsvReader csvReader;

	@Autowired
	private PubsubPublisher pubsubPublisher;

	@Value("${spring.cloud.gcp.pubsub.topic.employee.task}")
	private String topicName;

	/**
	 * @param id
	 * @param name
	 * @return employee
	 * @throws RequestValidationFailedException
	 */
	public Employee findByIdOrName(Long id, String name) throws RequestValidationFailedException {

		requestValidator.validateFilters(id, name);

		if (id != null)
			return employeeRepository.findById(id).orElseThrow();

		return employeeRepository.findByName(name).orElseThrow();
	}

	/**
	 * @param employee
	 * @return employee
	 */
	public Employee save(EmployeeEntry employee) {
		return employeeRepository.save(new Employee(employee.getName(), employee.getAge()));
	}

	/**
	 * @param updateEmployeeRequest
	 * @return employee
	 * @throws RequestValidationFailedException
	 */
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

	/**
	 * @param employees
	 * @return uploadEmployeeResponse
	 * @throws RequestValidationFailedException
	 */
	public ResponseEntity<UploadEmployeeResponse> process(TaskRequest taskRequest)
			throws RequestValidationFailedException {

		requestScopedParameter.setTaskId(taskRequest.getTaskId());
		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.SUBMITTED);

		employeeTaskExecutor.execute(taskRequest.getEmployees());
		UploadEmployeeResponse response = new UploadEmployeeResponse();
		response.setTaskId(requestScopedParameter.getTaskId());
		response.setStatus(requestScopedParameter.getLatestStatus());

		return ResponseEntity.ok(response);
	}

	/**
	 * @param id
	 * @param name
	 * @return employee
	 * @throws RequestValidationFailedException
	 */
	public Employee delete(Long id, String name) throws RequestValidationFailedException {
		// Delete operations are very rare. Hence, 1 extra query is preferred to return
		// deleted employees.
		Employee employee = findByIdOrName(id, name);
		if (id != null) {
			employeeRepository.deleteById(id);
		} else {
			employeeRepository.deleteByName(name);
		}
		return employee;
	}

	/**
	 * @param file
	 * @return uploadEmployeeResponse
	 * @throws Exception
	 */
	public ResponseEntity<UploadEmployeeResponse> upload(MultipartFile file) throws Exception {
		String uuid = UUID.randomUUID().toString();
		List<EmployeeEntry> employees = csvReader.readFromFile(file);
		pubsubPublisher.publish(topicName, new ObjectMapper().writeValueAsString(new TaskRequest(uuid, employees)));
		UploadEmployeeResponse response = new UploadEmployeeResponse();
		response.setTaskId(uuid);
		response.setStatus(CurrentTaskStatus.SUBMITTED.toString());

		return ResponseEntity.ok(response);
	}

	/**
	 * @param employees
	 * @param count
	 * @return resource
	 * @throws RequestValidationFailedException
	 * @throws IOException
	 */
	public ResponseEntity<Resource> download(List<String> employees, Integer count)
			throws RequestValidationFailedException, IOException {
		requestValidator.validateRequestParam(employees, count);
		if (count != null) {
			return download(count);
		}
		return download(employees);
	}

	/**
	 * @param employees
	 * @return resource
	 * @throws IOException
	 * @throws RequestValidationFailedException
	 */
	public ResponseEntity<Resource> download(List<String> employees)
			throws IOException, RequestValidationFailedException {

		requestValidator.validateDataForCSVWrite(employees);

		File file = csvWriter.writeValuesToFile(employees);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		respHeaders.setContentLength(file.length());
		String fileName = String.format(EMPLOYEE_S_CSV, UUID.randomUUID().toString().substring(0, 3));
		respHeaders.setContentDispositionFormData(Constants.ATTACHMENT, fileName);
		return ResponseEntity.ok().headers(respHeaders).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	/**
	 * @param count
	 * @return resource
	 * @throws IOException
	 * @throws RequestValidationFailedException
	 */
	public ResponseEntity<Resource> download(int count) throws IOException, RequestValidationFailedException {
		List<String> employees = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			employees.add(UUID.randomUUID().toString() + ":" + (int) (80 - 60 * Math.random()));
		}

		requestValidator.validateDataForCSVWrite(employees);

		File file = csvWriter.writeValuesToFile(employees);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		respHeaders.setContentLength(file.length());
		String fileName = String.format(EMPLOYEE_S_CSV, UUID.randomUUID().toString().substring(0, 3));
		respHeaders.setContentDispositionFormData(Constants.ATTACHMENT, fileName);
		return ResponseEntity.ok().headers(respHeaders).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

}
