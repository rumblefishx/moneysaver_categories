package com.rumblesoftware.cat.business.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rumblesoftware.cat.exceptions.InternalValidationErrorException;
import com.rumblesoftware.cat.exceptions.ValidationException;
import com.rumblesoftware.cat.io.CandidateToValidationData;
import com.rumblesoftware.cat.model.CategoryEntity;
import com.rumblesoftware.cat.repository.CategoryRepository;

@Component
public class CategoryExistanceValidator extends BaseValidator<CandidateToValidationData> {
	
	@Autowired
	private CategoryRepository repository;
	
	private static final String EXCEPTION_MESSAGE_ID = "category.new.alreadyexists";
	
	private Logger log = LogManager.getLogger(CategoryExistanceValidator.class);
	
	@Override
	public void validate(CandidateToValidationData input) throws InternalValidationErrorException, ValidationException{
		
		log.debug("[Validation Layer] - Starting Category Validation...");
		
		Optional<CategoryEntity> cat = 
			repository.findCategoryByNameAndUser(
					input.getCustomerId(),
					input.getCategoryName());
		
		if(cat.isPresent()) {
			log.error("[validation layer] Category already exists...");
			throw new ValidationException(EXCEPTION_MESSAGE_ID);
		}
		
		if(nextValidator != null)
			nextValidator.validate(input);
	}

}
