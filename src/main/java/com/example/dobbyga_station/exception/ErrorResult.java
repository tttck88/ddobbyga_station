package com.example.dobbyga_station.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {

	DUPLICATE_USER_REGISTER(HttpStatus.BAD_REQUEST, "Duplicated Membership Register Request"),
	User_NOT_FOUND(HttpStatus.NOT_FOUND, "Membership Not found"),
	UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception");

	private final HttpStatus httpStatus;
	private final String message;
}