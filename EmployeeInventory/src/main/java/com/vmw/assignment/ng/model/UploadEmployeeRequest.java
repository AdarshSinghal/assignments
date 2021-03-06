package com.vmw.assignment.ng.model;

import java.util.List;

/**
 * @author adarsh
 *
 */
public class UploadEmployeeRequest {

	List<EmployeeEntry> employees;

	public List<EmployeeEntry> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeEntry> employees) {
		this.employees = employees;
	}

}
