package com.vmw.assignment.ng.model.dto;

import java.io.Serializable;

/**
 * @author adarsh
 *
 */
public class TaskStatusId implements Serializable {

	private static final long serialVersionUID = -8278835896898050759L;

	private String id;
	private String status;

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
