package com.rumblesoftware.cat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
import com.rumblesoftware.cat.io.input.dto.CategoryPatchInputDTO;
import com.rumblesoftware.cat.io.output.dto.CategoryOutputDTO;

@Service
public interface CategoryService {
	
	public CategoryOutputDTO addNewCategory(CategoryInputDTO input);
	public CategoryOutputDTO removeCategory(Long customerId,Long categoryId);
	public CategoryOutputDTO updateCategory(CategoryPatchInputDTO patch);
	
	public CategoryOutputDTO findCategoryById(Long customerId, Long categoryId);
	public List<CategoryOutputDTO> getAllCustomerCat(Long customerId);
}
