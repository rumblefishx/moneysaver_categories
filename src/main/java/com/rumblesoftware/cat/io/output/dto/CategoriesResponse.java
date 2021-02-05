package com.rumblesoftware.cat.io.output.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoriesResponse {
	
	private List<String> errors;
	private List<CategoryOutputDTO> bodyItems;
	
	public CategoriesResponse(){
		this.errors = new ArrayList<String>();
		this.bodyItems = new ArrayList<CategoryOutputDTO>();
	}
	
	public void setBodyItems(List<CategoryOutputDTO> bodyItems) {
		this.bodyItems = bodyItems;
	}
	public List<String> getErrors() {
		return errors;
	}
	public List<CategoryOutputDTO> getBodyItems() {
		return bodyItems;
	}
	
	public void addError(String errorMessage){
		errors.add(errorMessage);
	}
	
	
}
