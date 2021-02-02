package com.rumblesoftware.cat.io.validations.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rumblesoftware.cat.io.validations.ValidFilledRange;

public class ValidFilledRangeValidation implements ConstraintValidator<ValidFilledRange, String> {

	private Integer min = 0;
	private Integer max = 0;
	
	@Override
	public void initialize(ValidFilledRange constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value != null && value.trim().length() > 0)
			if(value.length() < min || value.length() > max)
				return false;
		
		return true;
	}

}
