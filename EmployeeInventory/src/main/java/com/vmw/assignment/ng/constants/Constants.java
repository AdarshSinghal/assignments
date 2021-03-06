package com.vmw.assignment.ng.constants;

/**
 * @author adarsh
 *
 */
public class Constants {

	private Constants() {
		throw new UnsupportedOperationException();
	}

	public static final int EMPLOYEE_MIN_AGE = 0;
	public static final int EMPLOYEE_MAX_AGE = 100;

	public static final String INVALID_INPUTS_RECEIVED = "Invalid inputs received";
	public static final String CSV = ".csv";
	public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
	public static final String NAME_AGE = "Name:Age";
	public static final String EMP_CSV = "emp.csv";
	public static final String ATTACHMENT = "attachment";

	// Exception Messages
	public static final String AGE_IS_NOT_IN_CORRECT_RANGE = "Age is not in correct range";
	public static final String REQUIRED_ONE_QUERY_PARAMETER_ID_OR_NAME = "Required one query parameter (id or name)";
	public static final String EXPECTED_A_VALUE_FOR_AGE_FIELD = "Expected a value for age field";
	public static final String INCORRECT_FORMAT_FOR_CSV = "Incorrect format. Please use \"Adarsh:25\" format";
	public static final String THE_REQUEST_IS_NOT_VALID = "The request is not valid";
	public static final String MAXIMUM_UPLOAD_SIZE_EXCEEDED = "Maximum upload size exceeded";
	public static final String EXACTLY_1_REQUEST_PARAMETER_IS_REQUIRED = "Exactly 1 request parameter is required";
	public static final String VALUE_FOR_COUNT_SHOULD_BE_IN_RANGE_1_1M = "Value for \"count\" should be in range [1,1M]";
	// Exception Messages

}
