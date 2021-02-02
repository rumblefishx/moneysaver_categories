package com.rumblesoftware.cat.exceptions;

public class CategoryNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5768297789423126833L;
	private static final String DEFAULT_ERROR_MESSAGE = "{category.not.found.message}";
	
	public CategoryNotFoundException() {
		super(DEFAULT_ERROR_MESSAGE);
	}

}
