package com.rumblesoftware.cat.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rumblesoftware.cat.exceptions.CategoryAlreadyExistsException;
import com.rumblesoftware.cat.exceptions.InvalidDataException;
import com.rumblesoftware.cat.exceptions.ValidationException;
import com.rumblesoftware.cat.io.IOConverter;
import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
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
	
	@PostMapping
	public ResponseEntity<CategoryResponse> addNewCategory(@RequestBody @Valid CategoryInputDTO request,BindingResult br){
		CategoryResponse out = new CategoryResponse(); 
		CategoryOutputDTO output = null;
		
		if(br.hasErrors()) {
			br.getAllErrors().forEach(e -> out.addErrorMessage(e.getDefaultMessage()));
			out.setBody(converter.castToOutput(request));
			return ResponseEntity.badRequest().body(out);
		}
		
		try {
			output = service.addNewCategory(request);
		} catch(ValidationException|InvalidDataException e) {
			output = converter.castToOutput(request);
			out.setBody(output);
			out.addErrorMessage(po.getMessage(e.getMessage()));
			return ResponseEntity.badRequest().body(out);
		}
		
		out.setBody(output);
		return ResponseEntity.ok(out);
	}  
	
}
