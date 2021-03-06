package com.vmw.assignment.ng.model;

public enum CurrentTaskStatus {

	SUBMITTED("Submitted for processing"), VALIDATION_STARTS("Request validation in progress"),
	VALIDATION_ENDS("Request validation successful"), CHECK_EMP_EXISTS("Validating employee existence"),
	SKIPPING_EMP_CREATION("Skipping employee creation"), NEW_EMP_DETECTED("New Employees records found"),
	CREATING_EMPLOYEES("Creating new employees"), CREATED_EMPLOYEES("Created new employees"),
	ANALYZE_AGE("Performing age analysis"), AGE_ANALYSIS_COMPLETED("Age analysis successfully completed"),
	COMPLETED("All tasks completed successfully"), INIT_FAILED("Could not start processing"),
	TASK_FAILED("Task execution failed");

	private String status;

	CurrentTaskStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.status;
	}

}
