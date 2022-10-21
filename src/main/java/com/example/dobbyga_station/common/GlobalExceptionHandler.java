package com.example.dobbyga_station.common;

import com.example.dobbyga_station.exception.CustomException;
import com.example.dobbyga_station.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		final MethodArgumentNotValidException ex,
		final HttpHeaders headers,
		final HttpStatus status,
		final WebRequest request) {

		final List<String> errorList = ex.getBindingResult()
			.getAllErrors()
			.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.collect(Collectors.toList());

		log.warn("Invalid request Parameter errors : {}", errorList);
		return this.makeErrorResponseEntity(errorList.toString());
	}

	@ExceptionHandler({CustomException.class})
	public ResponseEntity handleUserException(final CustomException exception) {
		log.warn("UserException occur : ", exception);
		return this.makeErrorResponseEntity(exception.getErrorResult().getHttpStatus(), exception.getMessage());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity handleException(final Exception exception) {
		log.warn("UserException occur : ", exception);
		return this.makeErrorResponseEntity(ErrorResult.UNKNOWN_EXCEPTION.getHttpStatus(), ErrorResult.UNKNOWN_EXCEPTION.getMessage());
	}

	private ResponseEntity<Object> makeErrorResponseEntity(final String errorDescription) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(errorDescription);
	}

	private ResponseEntity makeErrorResponseEntity(HttpStatus httpStatus, String message) {
		return ResponseEntity.status(httpStatus)
			.body(message);
	}
}
