package com.vmw.assignment.ng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vmw.assignment.ng.model.AddTaskStatusRequest;
import com.vmw.assignment.ng.model.GetTaskStatusResponse;
import com.vmw.assignment.ng.model.dto.TaskStatus;
import com.vmw.assignment.ng.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public ResponseEntity<GetTaskStatusResponse> getTaskStatus(String taskId) {
		List<TaskStatus> taskStatusList = taskRepository.findAllTasksByTaskId(taskId);
		GetTaskStatusResponse response = new GetTaskStatusResponse();
		if (taskStatusList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		response.setLatestStatus(taskStatusList.get(0).getStatus());
		response.setTaskStatusList(taskStatusList);
		return ResponseEntity.ok(response);
	}

	public TaskStatus save(AddTaskStatusRequest request) {
		TaskStatus taskStatus = new TaskStatus();
		taskStatus.setId(request.getId());
		taskStatus.setStatus(request.getStatus());
		return taskRepository.save(taskStatus);
	}

	public TaskStatus save(TaskStatus taskStatus) {
		return taskRepository.save(taskStatus);
	}

}