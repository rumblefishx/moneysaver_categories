package com.rumblesoftware.cat.business.impl;

import org.springframework.stereotype.Component;

import com.rumblesoftware.cat.exceptions.InternalValidationErrorException;
import com.rumblesoftware.cat.exceptions.ValidationException;

@Component
public abstract class BaseValidator<O> {

	protected BaseValidator<O> nextValidator;
	
	public void setNextVal(BaseValidator<O> val) {
		this.nextValidator = val;
	}
	
	public abstract void validate(O input) throws InternalValidationErrorException, ValidationException;
}
