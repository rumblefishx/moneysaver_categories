package com.rumblesoftware.cat.business.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rumblesoftware.cat.exceptions.InvalidDataException;
import com.rumblesoftware.cat.exceptions.ValidationException;
import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
import com.rumblesoftware.cat.model.CategoryEntity;
import com.rumblesoftware.cat.repository.CategoryRepository;

@Component
public class CategoryExistanceValidator extends BaseValidator<CategoryInputDTO> {
	
	@Autowired
	private CategoryRepository repository;
	
	private static final String EXCEPTION_MESSAGE_ID = "category.new.alreadyexists";
	
	@Override
	public void validate(CategoryInputDTO input) throws InvalidDataException, ValidationException{
		Optional<CategoryEntity> cat = 
			repository.findCategoryByNameAndUser(
					input.getCustomerId(),
					input.getCategoryName());
		
		if(cat.isPresent())
			throw new ValidationException(EXCEPTION_MESSAGE_ID);
		
		if(nextValidator != null)
			nextValidator.validate(input);
	}

}
