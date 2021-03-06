package com.vmw.assignment.ng.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vmw.assignment.ng.model.dto.TaskStatus;
import com.vmw.assignment.ng.repository.TaskRepository;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScopedParameter {

	private String taskId;

	private List<TaskStatus> taskStatusList;

	@Autowired
	private TaskRepository taskRepository;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<TaskStatus> getTaskStatusList() {
		if (taskStatusList == null)
			taskStatusList = new ArrayList<>();
		return taskStatusList;
	}

	public void setTaskStatusList(List<TaskStatus> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}

	public TaskRepository getTaskRepository() {
		return taskRepository;
	}

	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public void saveTaskStatus(CurrentTaskStatus currentTaskStatus) {
		TaskStatus taskStatus = new TaskStatus();
		taskStatus.setId(Objects.requireNonNull(taskId));
		taskStatus.setStatus(currentTaskStatus.toString());
		getTaskStatusList().add(taskStatus);
		taskRepository.save(taskStatus);
	}

}
