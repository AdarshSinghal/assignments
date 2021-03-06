package com.vmw.assignment.ng.exceptions;

import com.vmw.assignment.ng.constants.Constants;

public class RequestValidationFailedException extends Exception {

	private static final long serialVersionUID = 911359122820726609L;

	public RequestValidationFailedException() {
		super(Constants.THE_REQUEST_IS_NOT_VALID);
	}

	public RequestValidationFailedException(String message) {
		super(message);
	}

}
