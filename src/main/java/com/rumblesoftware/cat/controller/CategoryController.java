package com.rumblesoftware.cat.controller;


import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rumblesoftware.cat.exceptions.CategoryNotFoundException;
import com.rumblesoftware.cat.exceptions.InvalidDataException;
import com.rumblesoftware.cat.exceptions.ValidationException;
import com.rumblesoftware.cat.io.IOConverter;
import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
import com.rumblesoftware.cat.io.input.dto.CategoryPatchInputDTO;
import com.rumblesoftware.cat.io.output.dto.CategoryOutputDTO;
import com.rumblesoftware.cat.io.output.dto.CategoryResponse;
import com.rumblesoftware.cat.service.CategoryService;
import com.rumblesoftware.utils.PostOfficer;

@RestController(value = "/category")
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@Autowired
	private IOConverter converter;
	
	@Autowired
	private PostOfficer po;
	
	private Logger log = LogManager.getLogger(CategoryController.class);
	
	@RequestMapping(method = RequestMethod.POST,value = "/category")
	public ResponseEntity<CategoryResponse> addNewCategory(@RequestBody @Valid CategoryInputDTO request,BindingResult br){
		CategoryResponse out = new CategoryResponse(); 
		CategoryOutputDTO output = null;
		
		log.info("[Controller Layer] (Add new category) receiving request...");
		
		if(br.hasErrors()) {
			log.info("[Controller Layer] (Add new category) delivering validation errors...");
			br.getAllErrors().forEach(e -> out.addErrorMessage(e.getDefaultMessage()));
			out.setBody(converter.castToOutput(request));
			return ResponseEntity.badRequest().body(out);
		}
		
		try {
			log.info("[Controller Layer] (Add new category) Calling service layer...");
			output = service.addNewCategory(request);
			log.info("[Controller Layer] (Add new category) Leaving service layer...");
		} catch(ValidationException|InvalidDataException e) {
			log.info("[Controller Layer] (Add new category) returning an exception");
			output = converter.castToOutput(request);
			out.setBody(output);
			out.addErrorMessage(po.getMessage(e.getMessage()));			
			return ResponseEntity.badRequest().body(out);
		}
		log.info("[Controller Layer] (Add new category) returning result");
		
		out.setBody(output);
		return ResponseEntity.ok(out);
	}  
	
	@RequestMapping(method = RequestMethod.PATCH,value = "/category")
	public ResponseEntity<CategoryResponse> updateCategory(
			@RequestBody @Valid CategoryPatchInputDTO input,
			BindingResult br){
		log.info("[Controller Layer] (Update category) receiving request...");
		CategoryResponse response = new CategoryResponse();
		CategoryOutputDTO output = null;
		
		if(br.hasErrors()) {
			log.info("[Controller Layer] (Update category) delivering validation errors...");
			br.getAllErrors()
			.forEach(e -> response.addErrorMessage(e.getDefaultMessage()));
			response.setBody(converter.castToOutput(input));
			return ResponseEntity.badRequest().body(response);
		}
		
		try {
			 log.info("[Controller Layer] (Update category) Calling service layer...");
			 output = service.updateCategory(input);
			 log.info("[Controller Layer] (Update category) Leaving service layer...");
		} catch(CategoryNotFoundException|ValidationException
				|InvalidDataException e) {
			log.info("[Controller Layer] (Update category) returning an exception");
			output = converter.castToOutput(input);
			response.setBody(output);
			response.addErrorMessage(po.getMessage(e.getMessage()));	
			return ResponseEntity.badRequest().body(response);	
		}
		log.info("[Controller Layer] (Update category) returning result");
		response.setBody(output);
		
		return ResponseEntity.ok(response);
	}
	
}
