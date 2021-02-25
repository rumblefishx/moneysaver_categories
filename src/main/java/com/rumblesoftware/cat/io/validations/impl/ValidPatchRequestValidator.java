package com.rumblesoftware.cat.io.validations.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rumblesoftware.cat.io.input.dto.CategoryPatchInputDTO;
import com.rumblesoftware.cat.io.validations.ValidPatchRequest;

public class ValidPatchRequestValidator implements ConstraintValidator<ValidPatchRequest, CategoryPatchInputDTO> {

	@Override
	public boolean isValid(CategoryPatchInputDTO request, ConstraintValidatorContext context) {
		
		boolean validName = 
				request.getCategoryName() != null && 
				request.getCategoryName().trim().length() > 0;
		
		boolean validDescription = 
				request.getCategoryDescription() != null && 
				request.getCategoryDescription().trim().length()>0;
		
		if(validName || validDescription)
			return true;
		return false;
	}

}
