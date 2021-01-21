package com.rumblesoftware.cat.io.input.dto;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import com.sun.istack.NotNull;

@Validated
public class CategoryPatchDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3607975843370464931L;

	@NotNull
	private Long customerId;
	
	@NotNull
	private Long categoryId;
	
	
	private String categoryName;
	
	private String categoryDescription;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	
	
}
