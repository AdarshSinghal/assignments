package com.vmw.assignment.ng.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.dto.Employee;
import com.vmw.assignment.ng.model.response.UploadEmployeeResponse;
import com.vmw.assignment.ng.service.EmployeeService;

//@WebMvcTest(controllers = EmployeeController.class)
//@TestInstance(Lifecycle.PER_CLASS)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EmployeeService employeeService;

	// @Test
	void testFindByIdOrName() throws Exception {
		Employee employee = new Employee();
		employee.setId(1l);
		employee.setName("Adarsh");
		employee.setAge(25);

		when(employeeService.findByIdOrName(Mockito.any(), Mockito.any())).thenReturn(employee);

		mockMvc.perform(get("/api/employees?id=1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(new ObjectMapper().writeValueAsString(employee))).andReturn();
	}

	// @Test
	void upload() throws Exception {
		UploadEmployeeResponse uploadEmployeeResponse = new UploadEmployeeResponse();
		uploadEmployeeResponse.setStatus(CurrentTaskStatus.COMPLETED.toString());
		ResponseEntity<UploadEmployeeResponse> response = ResponseEntity.ok(uploadEmployeeResponse);

		// when(employeeService.upload(Mockito.any())).thenReturn(response);
		byte[] fileData = CurrentTaskStatus.COMPLETED.toString().getBytes();
		mockMvc.perform(
				post("/api/employees/upload?file=").content(fileData).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(CoreMatchers.anything()))
				.andReturn();
	}

}
