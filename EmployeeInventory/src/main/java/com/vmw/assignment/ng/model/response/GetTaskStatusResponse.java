package com.vmw.assignment.ng.model.response;

import java.util.List;

import com.vmw.assignment.ng.model.dto.TaskStatus;

public class GetTaskStatusResponse {

	private String latestStatus;

	private List<TaskStatus> taskStatusList;

	public String getLatestStatus() {
		return latestStatus;
	}

	public void setLatestStatus(String latestStatus) {
		this.latestStatus = latestStatus;
	}

	public List<TaskStatus> getTaskStatusList() {
		return taskStatusList;
	}

	public void setTaskStatusList(List<TaskStatus> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}

}
