package com.vmw.assignment.ng.model.request;

import java.util.List;

import com.vmw.assignment.ng.model.EmployeeEntry;

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
