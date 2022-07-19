package com.test.web.app.test_wep_app.controllers;

import com.test.web.app.test_wep_app.dto.ExceptionResponse;
import com.test.web.app.test_wep_app.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ImagesControllerAdvice {

	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ExceptionResponse imageNotFoundHandler(ObjectNotFoundException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
}
