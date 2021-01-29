package com.rumblesoftware.cat.exceptions;

public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8827497635511950912L;

	public ValidationException(String message) {
		super(message);
	}
}
