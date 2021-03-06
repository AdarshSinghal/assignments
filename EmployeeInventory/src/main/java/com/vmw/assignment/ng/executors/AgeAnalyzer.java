package com.vmw.assignment.ng.executors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.RequestScopedParameter;

@Component
public class AgeAnalyzer {

	@Autowired
	RequestScopedParameter requestScopedParameter;

	public void performAgeAnalysis(List<EmployeeEntry> employees) {

		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.ANALYZE_AGE);
		// Performing age analysis

		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.AGE_ANALYSIS_COMPLETED);
	}

}
