package com.rumblesoftware.cat.io;

public class CandidateToValidationData {
	
	private Long customerId;
	private String categoryName;
	
	
	public CandidateToValidationData(Long customerId, String categoryName) {
		super();
		this.customerId = customerId;
		this.categoryName = categoryName;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	
	

}
