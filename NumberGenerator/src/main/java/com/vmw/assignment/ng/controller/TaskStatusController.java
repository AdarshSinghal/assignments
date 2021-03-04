package com.vmw.assignment.ng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vmw.assignment.ng.model.AddTaskStatusRequest;
import com.vmw.assignment.ng.model.GetTaskStatusResponse;
import com.vmw.assignment.ng.model.dto.TaskStatus;
import com.vmw.assignment.ng.service.TaskService;

@RestController
@RequestMapping("/api/status")
public class TaskStatusController {

	@Autowired
	private TaskService taskService;

	@GetMapping("/{taskId}")
	public ResponseEntity<GetTaskStatusResponse> getTaskStatus(@PathVariable String taskId) {
		return taskService.getTaskStatus(taskId);

	}

	@PostMapping
	public ResponseEntity<TaskStatus> save(@RequestBody AddTaskStatusRequest request) {
		return ResponseEntity.ok(taskService.save(request));

	}

}
