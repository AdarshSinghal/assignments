package com.vmw.assignment.ng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vmw.assignment.ng.model.response.GetTaskStatusResponse;
import com.vmw.assignment.ng.service.TaskService;

/**
 * Operations for Task Status
 * 
 * @author adarsh
 *
 */
@RestController
@RequestMapping("/api/status")
public class TaskStatusController {

	@Autowired
	private TaskService taskService;

	@GetMapping("/{taskId}")
	//@ApiOperation(value = "Find task status by task id.")
	public ResponseEntity<GetTaskStatusResponse> getTaskStatus(@PathVariable String taskId) {
		return taskService.getTaskStatus(taskId);
	}

}
