package com.windf.core.exception;

import java.io.Serializable;

public class CodeException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type;

	public CodeException() {
	}

	public CodeException(String message) {
		super(message);
	}

	public CodeException(String type, String message) {
	}

	public CodeException(Throwable cause) {
		super(cause);
	}

	public CodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CodeException(String type, String message, Throwable cause) {
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
