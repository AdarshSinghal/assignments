package com.vmw.assignment.ng.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.response.GetTaskStatusResponse;
import com.vmw.assignment.ng.service.TaskService;

//@WebMvcTest(controllers = TaskStatusController.class)
//@TestInstance(Lifecycle.PER_CLASS)
class TaskStatusControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TaskService taskService;

	// @Test
	void testFindByIdOrName() throws Exception {
		GetTaskStatusResponse getTaskStatusResponse = new GetTaskStatusResponse();
		getTaskStatusResponse.setLatestStatus(CurrentTaskStatus.COMPLETED.toString());
		ResponseEntity<GetTaskStatusResponse> response = ResponseEntity.ok(getTaskStatusResponse);

		when(taskService.getTaskStatus(Mockito.anyString())).thenReturn(response);

		mockMvc.perform(get("/api/status?taskId=1234")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(new ObjectMapper().writeValueAsString(getTaskStatusResponse))).andReturn();
	}

}
