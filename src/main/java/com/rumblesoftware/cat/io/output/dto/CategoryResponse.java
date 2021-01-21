package com.rumblesoftware.cat.io.output.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -130150528108305444L;
	private List<String> errors;
	private CategoryOutputDTO body;
	
	public CategoryResponse() {
		this.errors = new ArrayList<String>();
	}
	public CategoryResponse(List<String> errors, CategoryOutputDTO body) {
		super();
		this.errors = errors;
		this.body = body;
	}
	public CategoryResponse(CategoryOutputDTO body) {
		super();
		this.body = body;
	}
	public List<String> getErrors() {
		return errors;
	}

	public void addErrorMessage(String message) {
		this.errors.add(message);
	}
	public CategoryOutputDTO getBody() {
		return body;
	}
	public void setBody(CategoryOutputDTO body) {
		this.body = body;
	}
	
}
