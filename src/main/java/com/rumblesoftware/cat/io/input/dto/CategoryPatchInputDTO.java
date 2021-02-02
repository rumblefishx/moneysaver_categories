package com.rumblesoftware.cat.io.input.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.rumblesoftware.cat.io.validations.ValidFilledRange;
import com.rumblesoftware.cat.io.validations.ValidPatchRequest;

@Valid
@ValidPatchRequest
public class CategoryPatchInputDTO {
	
	@NotNull(message = "{category.patch.input.customerId.null}")
	private Long customerId;
	
	@NotNull(message = "{category.patch.input.categoryId.null}")
	private Long categoryId;	
	
	@ValidFilledRange(min=4,max=20,message="{category.patch.input.categoryName.range}")
	private String categoryName;
	
	@ValidFilledRange(min=15,max=70,message="{category.patch.input.categoryDescription.range}")
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
