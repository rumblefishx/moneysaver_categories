package com.rumblesoftware.cat.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_ERROR_MESSAGE = "category.new.alreadyexists";

	public CategoryAlreadyExistsException() {
		super(DEFAULT_ERROR_MESSAGE);
	} 
}
