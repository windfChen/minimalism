package com.windf.core.exception;

public class UserException extends AbstractTypeException {

	private static final long serialVersionUID = 1L;
	
	public UserException() {
	}

	public UserException(String message) {
		super(message);
	}
	
	public UserException(String type, String message) {
		super(type, message);
	}

	public UserException(Throwable cause) {
		super(cause);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UserException(String type, String message, Throwable cause) {
		super(type, message, cause);
	}
}
