package com.windf.core.exception;

import java.io.Serializable;

public abstract class AbstractTypeException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;

	public AbstractTypeException() {
	}

	public AbstractTypeException(String message) {
		super(message);
	}
	
	public AbstractTypeException(String type, String message) {
		super(message);
		this.type = type;
	}

	public AbstractTypeException(Throwable cause) {
		super(cause);
	}

	public AbstractTypeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AbstractTypeException(String type, String message, Throwable cause) {
		super(message, cause);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
