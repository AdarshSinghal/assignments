package com.vmw.assignment.ng.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.vmw.assignment.ng.constants.Constants;
import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;

/**
 * Validation operations are maintained into this class
 * 
 * @author adarsh
 *
 */
@Component
public class RequestValidator {

	/**
	 * Validates the input age.
	 * 
	 * @param requestedAge
	 * @throws RequestValidationFailedException
	 */
	public void validateAge(Integer requestedAge) throws RequestValidationFailedException {
		if (requestedAge == null)
			throw new RequestValidationFailedException(Constants.EXPECTED_A_VALUE_FOR_AGE_FIELD);
		if (requestedAge < Constants.EMPLOYEE_MIN_AGE || requestedAge > Constants.EMPLOYEE_MAX_AGE) {
			throw new RequestValidationFailedException(Constants.AGE_IS_NOT_IN_CORRECT_RANGE);
		}
	}

	/**
	 * validates the list before writing into CSV.
	 * 
	 * @param employeeDetails
	 * @throws RequestValidationFailedException
	 */
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

	/**
	 * Validates the request parameter filter.
	 * 
	 * @param id
	 * @param name
	 * @throws RequestValidationFailedException
	 */
	public void validateFilters(Long id, String name) throws RequestValidationFailedException {
		boolean blankName = StringUtils.isBlank(name);
		if ((id == null && blankName) || (id != null && !blankName))
			throw new RequestValidationFailedException(Constants.REQUIRED_ONE_QUERY_PARAMETER_ID_OR_NAME);
	}

	/**
	 * Validates the request parameter filter
	 * 
	 * @param employees
	 * @param count
	 * @throws RequestValidationFailedException
	 */
	public void validateRequestParam(List<String> employees, Integer count) throws RequestValidationFailedException {
		boolean emptyList = employees == null || employees.isEmpty();
		if ((emptyList && count == null) || (!emptyList && count != null)) {
			throw new RequestValidationFailedException(Constants.EXACTLY_1_REQUEST_PARAMETER_IS_REQUIRED);
		}

		if (count!=null && (count < 1 || count > 1000000)) {
			throw new RequestValidationFailedException(Constants.VALUE_FOR_COUNT_SHOULD_BE_IN_RANGE_1_1M);
		}

	}

}
