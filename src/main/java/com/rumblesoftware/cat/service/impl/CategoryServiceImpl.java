package com.rumblesoftware.cat.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumblesoftware.cat.business.impl.CategoryExistanceValidator;
import com.rumblesoftware.cat.business.impl.UserExistanceValidator;
import com.rumblesoftware.cat.exceptions.CategoryNotFoundException;
import com.rumblesoftware.cat.io.CandidateToValidationData;
import com.rumblesoftware.cat.io.IOConverter;
import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
import com.rumblesoftware.cat.io.input.dto.CategoryPatchInputDTO;
import com.rumblesoftware.cat.io.output.dto.CategoryOutputDTO;
import com.rumblesoftware.cat.model.CategoryEntity;
import com.rumblesoftware.cat.repository.CategoryRepository;
import com.rumblesoftware.cat.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private IOConverter converter;
	
	@Autowired
	private CategoryExistanceValidator catVal;
	
	@Autowired
	private UserExistanceValidator userVal;
	
	@Override
	public CategoryOutputDTO addNewCategory(CategoryInputDTO input) {
		CategoryEntity category = null;
		CategoryOutputDTO output = null;
		
		CandidateToValidationData dataToValidate = 
				new CandidateToValidationData(
						input.getCustomerId(), 
						input.getCategoryName());
		
		//Calling Validations
		catVal.setNextVal(userVal);
		catVal.validate(dataToValidate);
		
		category = converter.castToEntity(input);
		category = repository.save(category);
		
		output = converter.castToOutput(category);
		
		return output;
	}

	@Override
	public CategoryOutputDTO removeCategory(Long customerId, Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryOutputDTO updateCategory(CategoryPatchInputDTO patch) {
		CategoryEntity updatedCategory = null;
		Optional<CategoryEntity> actualCategory = null;
		
		//Transfer data to a mid layer dto in order to validate them
		CandidateToValidationData dataToValidate = 
				new CandidateToValidationData(
						patch.getCustomerId(), 
						patch.getCategoryName());
		
		//Calling Validations
		catVal.setNextVal(userVal);
		catVal.validate(dataToValidate);
		
		//Search the category in the database
		actualCategory = 
				repository.findCategoryByIds(
						patch.getCustomerId(), 
						patch.getCategoryId());
		
		//Throw an exception in case of category not found
		if(actualCategory.isPresent() == false)
			throw new CategoryNotFoundException();
		
		
		//Cast input data into a entity instance
		updatedCategory = 
				converter.updateEntityData(actualCategory.get(), patch);	
		
		//Save changes in the database
		updatedCategory = repository.save(updatedCategory);
		
		//Cast the entity to output format and return it
		return converter.castToOutput(updatedCategory);
	}

	@Override
	public CategoryOutputDTO findCategoryById(Long customerId, Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryOutputDTO> getAllCustomerCat(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
