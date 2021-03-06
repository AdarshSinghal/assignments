package com.vmw.assignment.ng.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.UploadEmployeeResponse;
import com.vmw.assignment.ng.model.dto.Employee;
import com.vmw.assignment.ng.model.request.UpdateEmployeeRequest;
import com.vmw.assignment.ng.service.EmployeeService;

import io.swagger.annotations.ApiOperation;

/**
 * Operations for Employees.
 * 
 * @author adarsh
 *
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	@ApiOperation(value = "Find Employee by Id or Name. Exactly One query parameter should be used.")
	public Employee findByIdOrName(@RequestParam(required = false) Long id, @RequestParam(required = false) String name)
			throws RequestValidationFailedException {
		return employeeService.findByIdOrName(id, name);
	}

	@PostMapping
	@ApiOperation(value = "Add new employee. All fields are mandatory.")
	public ResponseEntity<Employee> save(@RequestBody EmployeeEntry employee) {
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employee));
	}

	@PutMapping
	@ApiOperation(value = "Update employee detail. All fields are mandatory.")
	public ResponseEntity<Employee> update(@RequestBody UpdateEmployeeRequest employee)
			throws RequestValidationFailedException {
		return ResponseEntity.ok(employeeService.update(employee));
	}

	@DeleteMapping
	@ApiOperation(value = "Delete Employee by Id or Name. Exactly One query parameter should be used.")
	public ResponseEntity<Employee> delete(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String name) throws RequestValidationFailedException {
		return ResponseEntity.ok(employeeService.delete(id, name));
	}

	@RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "Upload employee.csv and perform batch job on records.")
	public ResponseEntity<UploadEmployeeResponse> upload(@RequestParam("file") MultipartFile file)
			throws IOException, RequestValidationFailedException {
		return employeeService.upload(file);
	}

	@PostMapping("/download")
	@ApiOperation(value = "Generate and Download the CSV file through Employee details passed as request parameter. [Format for 1 record: Adarsh:27]")
	public ResponseEntity<Resource> download(@RequestParam(required = false) List<String> employees,
			@RequestParam(required = false) Integer count) throws RequestValidationFailedException, IOException {
		return employeeService.download(employees, count);
	}

	@PostMapping("/process")
	public ResponseEntity<UploadEmployeeResponse> process(@RequestBody List<EmployeeEntry> employees)
			throws RequestValidationFailedException {
		return employeeService.process(employees);
	}

}
