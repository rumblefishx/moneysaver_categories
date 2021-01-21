package com.rumblesoftware.cat.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryAlreadyExistsException() {
		super("category.new.alreadyexists");
	} 
}
