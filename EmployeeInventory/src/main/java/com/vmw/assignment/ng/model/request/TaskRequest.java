package com.vmw.assignment.ng.model.request;

import java.util.List;

import com.vmw.assignment.ng.model.EmployeeEntry;

/**
 * @author adarsh
 *
 */
public class TaskRequest {

	private String taskId;
	private List<EmployeeEntry> employees;

	public TaskRequest() {
	}

	public TaskRequest(String taskId, List<EmployeeEntry> employees) {
		this.taskId = taskId;
		this.employees = employees;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<EmployeeEntry> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeEntry> employees) {
		this.employees = employees;
	}

}
