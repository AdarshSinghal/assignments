package com.vmw.assignment.ng.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vmw.assignment.ng.constants.Constants;
import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;

@Component
public class RequestValidator {

	public void validateAge(Integer requestedAge) throws RequestValidationFailedException {
		if (requestedAge == null)
			throw new RequestValidationFailedException(Constants.EXPECTED_A_VALUE_FOR_AGE_FIELD);
		if (requestedAge < Constants.EMPLOYEE_MIN_AGE || requestedAge > Constants.EMPLOYEE_MAX_AGE) {
			throw new RequestValidationFailedException(Constants.AGE_IS_NOT_IN_CORRECT_RANGE);
		}
	}

	public void validateDataForCSVWrite(List<String> employeeDetails) throws RequestValidationFailedException {

		if (employeeDetails.isEmpty())
			throw new RequestValidationFailedException(Constants.INCORRECT_FORMAT_FOR_CSV);

		for (String ed : employeeDetails) {
			String[] splittedDetails = ed.split(":");
			if (splittedDetails.length != 2) {
				throw new RequestValidationFailedException(Constants.INCORRECT_FORMAT_FOR_CSV);
			}
		}

	}

}
