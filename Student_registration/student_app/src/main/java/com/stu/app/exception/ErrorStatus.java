package com.stu.app.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
	NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND),
    INVALID_HEADER("INVALID_TOKEN", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("INVALID_TOKEN", HttpStatus.UNAUTHORIZED),
    INVALID_LOGIN_DETAILS("INVALID_LOGIN_DETAILS", HttpStatus.UNAUTHORIZED),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPLOYEE_NOT_FOUND("EMPLOYEE_NOT_FOUND", HttpStatus.NOT_FOUND),
    DUPLICATE_RECORD("DUPLICATE_RECORD", HttpStatus.BAD_REQUEST),
    NOT_ACCEPTABLE("NOT_ACCEPTABLE", HttpStatus.NOT_ACCEPTABLE),
    INVALID_REQUEST("INVALID_REQUEST", HttpStatus.BAD_REQUEST),
	PROBLEM_TO_AUTHENTICATE("PROBLEM_TO_AUTHENTICATE", HttpStatus.BAD_GATEWAY);

    private final String code;
    private final HttpStatus httpStatus;

}
