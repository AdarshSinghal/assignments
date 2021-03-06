package com.vmw.assignment.ng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vmw.assignment.ng.model.dto.TaskStatus;
import com.vmw.assignment.ng.model.request.AddTaskStatusRequest;
import com.vmw.assignment.ng.model.response.GetTaskStatusResponse;
import com.vmw.assignment.ng.repository.TaskRepository;

/**
 * Business logics for Task Status operations are maintained in this class.
 * 
 * @author adarsh
 *
 */
@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	/**
	 * @param taskId
	 * @return getTaskStatusResponse
	 */
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

	/**
	 * @param request
	 * @return taskStatus
	 */
	public TaskStatus save(AddTaskStatusRequest request) {
		TaskStatus taskStatus = new TaskStatus();
		taskStatus.setId(request.getId());
		taskStatus.setStatus(request.getStatus());
		return taskRepository.save(taskStatus);
	}

	/**
	 * @param taskStatus
	 * @return taskStatus
	 */
	public TaskStatus save(TaskStatus taskStatus) {
		return taskRepository.save(taskStatus);
	}

}
