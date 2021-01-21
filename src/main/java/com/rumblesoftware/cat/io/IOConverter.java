package com.rumblesoftware.cat.io;

import org.springframework.stereotype.Component;

import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
import com.rumblesoftware.cat.io.input.dto.CategoryPatchDTO;
import com.rumblesoftware.cat.io.output.dto.CategoryOutputDTO;
import com.rumblesoftware.cat.model.CategoryEntity;


@Component
public class IOConverter {

	public CategoryEntity castToEntity(CategoryInputDTO input) {
		CategoryEntity entity = new CategoryEntity();
		
		entity.setCategoryDescription(input.getCategoryDescription());
		entity.setCategoryName(input.getCategoryName());
		entity.setCustomerId(input.getCustomerId());
		
		return entity;
	}
	
	public CategoryOutputDTO castToOutput(CategoryEntity entity) {
		CategoryOutputDTO output = new CategoryOutputDTO();
		output.setCategoryDescription(entity.getCategoryDescription());
		output.setCategoryId(entity.getCategoryId());
		output.setCategoryName(entity.getCategoryName());
		output.setCustomerId(entity.getCustomerId());
		return output;
	}
	
	public CategoryOutputDTO castToOutput(CategoryInputDTO input) {
		CategoryOutputDTO output = new CategoryOutputDTO();
		output.setCategoryDescription(input.getCategoryDescription());
		output.setCategoryName(input.getCategoryName());
		output.setCustomerId(input.getCustomerId());
		return output;
	}
	
	public CategoryEntity updateEntityData(CategoryEntity entity,CategoryPatchDTO patch) {
		if(patch.getCategoryDescription() != null 
				&& patch.getCategoryDescription().trim().length() != 0)
		entity.setCategoryDescription(patch.getCategoryDescription());
		
		if(patch.getCategoryName() != null 
				&& patch.getCategoryName().trim().length() != 0)
		entity.setCategoryName(patch.getCategoryName());
		return entity;
		
	}
}
