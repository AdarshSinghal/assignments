package com.vmw.assignment.ng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.UploadEmployeeResponse;
import com.vmw.assignment.ng.model.dto.Employee;
import com.vmw.assignment.ng.service.EmployeeService;

/**
 * @author adarsh
 *
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/{id}")
	public Employee findById(@PathVariable Long id) {
		return employeeService.findById(id);
	}

	@PostMapping
	public Employee save(@RequestBody EmployeeEntry employee) {
		return employeeService.save(employee);

	}

	@PostMapping("/process")
	public ResponseEntity<UploadEmployeeResponse> process(@RequestBody List<EmployeeEntry> employees)
			throws RequestValidationFailedException {
		return employeeService.process(employees);

	}

}
