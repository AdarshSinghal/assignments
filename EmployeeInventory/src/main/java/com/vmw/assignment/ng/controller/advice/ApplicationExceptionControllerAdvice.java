
package com.vmw.assignment.ng.controller.advice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.google.api.gax.rpc.NotFoundException;
import com.vmw.assignment.ng.constants.Constants;
import com.vmw.assignment.ng.exceptions.RequestValidationFailedException;

import io.grpc.StatusRuntimeException;

/**
 * Exceptions should be handled here. If no exception matchees the current
 * exception, then it returns the exception message provided by java.
 * 
 * @author adarsh
 *
 */
@RestControllerAdvice
public class ApplicationExceptionControllerAdvice {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(Exception exception) {
		return ResponseEntity.badRequest().body(getResponseForException(exception));
	}

	@ExceptionHandler({ MaxUploadSizeExceededException.class })
	public ResponseEntity<String> fileSizeLimitExceededException(MaxUploadSizeExceededException exception) {
		return ResponseEntity.badRequest().body(Constants.MAXIMUM_UPLOAD_SIZE_EXCEEDED);
	}

	@ExceptionHandler({ ExecutionException.class })
	public ResponseEntity<String> pubsubTopicNotFound(ExecutionException exception) {
		String message = exception.getMessage();
		Throwable cause = exception.getCause();
		if (cause instanceof NotFoundException) {
			message = cause.getMessage();
			if (cause.getCause() instanceof StatusRuntimeException) {
				message = cause.getCause().getMessage();
			}
		}
		return ResponseEntity.badRequest().body(message);

	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<String> divException(Exception exception) {
		String message = null;
		Throwable cause = exception.getCause();
		if (cause instanceof ConstraintViolationException) {
			Throwable cause2 = cause.getCause();
			if (cause2 instanceof SQLIntegrityConstraintViolationException) {
				message = cause2.getMessage();
			}
		}
		if (message == null)
			message = Constants.INVALID_INPUTS_RECEIVED;
		return ResponseEntity.badRequest().body(message);
	}

	@ExceptionHandler({ NoSuchElementException.class, EmptyResultDataAccessException.class })
	public ResponseEntity<String> exception(RuntimeException exception) {
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(value = RequestValidationFailedException.class)
	public ResponseEntity<String> exception(RequestValidationFailedException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());
	}

	private String getResponseForException(Exception exception) {
		return exception.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
