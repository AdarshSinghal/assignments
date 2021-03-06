package com.vmw.assignment.ng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.UploadEmployeeResponse;
import com.vmw.assignment.ng.model.dto.Employee;
import com.vmw.assignment.ng.model.request.UpdateEmployeeRequest;
import com.vmw.assignment.ng.service.EmployeeService;

import io.swagger.annotations.ApiOperation;

/**
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
	public Employee save(@RequestBody EmployeeEntry employee) {
		return employeeService.save(employee);
	}

	@PutMapping
	@ApiOperation(value = "Update employee detail. All fields are mandatory.")
	public Employee update(@RequestBody UpdateEmployeeRequest employee) throws RequestValidationFailedException {
		return employeeService.update(employee);
	}

	@DeleteMapping
	@ApiOperation(value = "Delete Employee by Id or Name. Exactly One query parameter should be used.")
	public void delete(@RequestParam(required = false) Long id, @RequestParam(required = false) String name)
			throws RequestValidationFailedException {
		employeeService.delete(id, name);
	}

	@PostMapping("/process")
	public ResponseEntity<UploadEmployeeResponse> process(@RequestBody List<EmployeeEntry> employees)
			throws RequestValidationFailedException {
		return employeeService.process(employees);

	}

}
