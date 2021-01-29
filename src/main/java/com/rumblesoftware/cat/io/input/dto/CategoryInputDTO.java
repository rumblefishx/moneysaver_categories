package com.rumblesoftware.cat.io.input.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Validated
public class CategoryInputDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677532765417210895L;

	@NotNull(message = "{category.new.input.customerId.null}")
	private Long customerId;

	@NotBlank(message = "{category.new.input.categoryName.blank}")
	@Length(min = 4, max = 20, message = "{category.new.input.categoryName.range}")
	private String categoryName;

	@NotBlank(message = "{category.new.input.categoryDescription.blank}")
	@Length(min = 15, max = 70, message = "{category.new.input.categoryDescription.range}")
	private String categoryDescription;

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

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

}
