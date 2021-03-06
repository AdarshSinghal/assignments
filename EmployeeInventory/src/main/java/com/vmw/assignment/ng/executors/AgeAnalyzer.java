package com.vmw.assignment.ng.executors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.assignment.ng.model.CurrentTaskStatus;
import com.vmw.assignment.ng.model.EmployeeEntry;
import com.vmw.assignment.ng.model.RequestScopedParameter;
import com.vmw.assignment.ng.utils.ThreadUtils;

/**
 * This class encapsulates the age analysis responsibilities.
 * 
 * @author adarsh
 *
 */
@Component
public class AgeAnalyzer {

	@Autowired
	RequestScopedParameter requestScopedParameter;

	@Autowired
	private ThreadUtils threadUtils;

	/**
	 * Perform analysis on employee's mentioned age based on their age in database.
	 * 
	 * @param employees
	 */
	public void performAgeAnalysis(List<EmployeeEntry> employees) {

		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.ANALYZE_AGE);
		// Performing age analysis
		threadUtils.sleep(10);
		requestScopedParameter.saveTaskStatus(CurrentTaskStatus.AGE_ANALYSIS_COMPLETED);
	}

}
