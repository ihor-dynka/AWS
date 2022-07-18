package com.test.web.app.test_wep_app.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	public ObjectNotFoundException(String message) {
		super("No object found with name " + message);
	}
}
