package com.vmw.assignment.ng.model;

/**
 * @author adarsh
 *
 */
public class AddTaskStatusRequest {

	private String id;
	private String status;

	public AddTaskStatusRequest() {
	}

	public AddTaskStatusRequest(String id, String status) {
		this.id = id;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
