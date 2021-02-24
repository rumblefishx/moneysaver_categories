package com.rumblesoftware.cat.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.rumblesoftware.cat.controller.CategoryController;
import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
import com.rumblesoftware.cat.io.input.dto.CategoryPatchInputDTO;
import com.rumblesoftware.cat.io.output.dto.CategoryOutputDTO;
import com.rumblesoftware.cat.model.CategoryEntity;


@Component
public class IOConverter {

	
	private Logger log = LogManager.getLogger(CategoryController.class);
	
	public CategoryEntity castToEntity(CategoryInputDTO input) {
		log.debug("Casting Input to Entity artifact...");		
		CategoryEntity entity = new CategoryEntity();
		
		entity.setCategoryDescription(input.getCategoryDescription());
		entity.setCategoryName(input.getCategoryName());
		entity.setCustomerId(input.getCustomerId());
		
		return entity;
	}
	
	public CategoryOutputDTO castToOutput(CategoryEntity entity) {
		log.debug("Casting entity artifact to output dto...");
		CategoryOutputDTO output = new CategoryOutputDTO();
		output.setCategoryDescription(entity.getCategoryDescription());
		output.setCategoryId(entity.getCategoryId());
		output.setCategoryName(entity.getCategoryName());
		output.setCustomerId(entity.getCustomerId());
		return output;
	}
	
	public CategoryOutputDTO castToOutput(CategoryInputDTO input) {
		log.debug("Casting input dto to output dto...");
		CategoryOutputDTO output = new CategoryOutputDTO();
		output.setCategoryDescription(input.getCategoryDescription());
		output.setCategoryName(input.getCategoryName());
		output.setCustomerId(input.getCustomerId());
		return output;
	}
	
	public CategoryOutputDTO castToOutput(CategoryPatchInputDTO input) {
		log.debug("Casting input dto to output dto...");
		CategoryOutputDTO output = new CategoryOutputDTO();
		output.setCategoryDescription(input.getCategoryDescription());
		output.setCategoryName(input.getCategoryName());
		output.setCustomerId(input.getCustomerId());
		output.setCategoryId(input.getCategoryId());
		return output;
	}
	
	public CategoryEntity updateEntityData(CategoryEntity entity,CategoryPatchInputDTO patch) {
		log.debug("Transfering updated data to entity artifact...");
		if(patch.getCategoryDescription() != null 
				&& patch.getCategoryDescription().trim().length() != 0)
		entity.setCategoryDescription(patch.getCategoryDescription());
		
		if(patch.getCategoryName() != null 
				&& patch.getCategoryName().trim().length() != 0)
		entity.setCategoryName(patch.getCategoryName());
		return entity;
		
	}
}
