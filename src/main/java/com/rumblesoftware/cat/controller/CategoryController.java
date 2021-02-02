package com.rumblesoftware.cat.controller;

import javax.validation.Valid;

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
	
	private static final String GENERIC_INTERNAL_ERROR_MSG = "{app.internal.error.message}";
	
	
	//********************************************
	//********************************************
	//********************************************
	//************* ATENÇÃO          *************
		//* Adicionar Logger.error nos locais onde lanca excecao
		//* Otimizar o catch aqui dos metodos do controller
	
	//********************************************
	//********************************************
	//********************************************
	
	
	@RequestMapping(method = RequestMethod.POST,value = "/category")
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
		} catch(Exception e) {
			output = converter.castToOutput(request);
			out.setBody(output);
			
			if(e instanceof ValidationException
			|| e instanceof InvalidDataException) {
				out.addErrorMessage(po.getMessage(e.getMessage()));
			} else {
				out.addErrorMessage(po.getMessage(GENERIC_INTERNAL_ERROR_MSG));
			}
			
			return ResponseEntity.badRequest().body(out);
		}
		
		out.setBody(output);
		return ResponseEntity.ok(out);
	}  
	
	@RequestMapping(method = RequestMethod.PATCH,value = "/category")
	public ResponseEntity<CategoryResponse> updateCategory(
			@RequestBody @Valid CategoryPatchInputDTO input,
			BindingResult br){
		
		CategoryResponse response = new CategoryResponse();
		CategoryOutputDTO output = null;
		
		if(br.hasErrors()) {
			br.getAllErrors()
			.forEach(e -> response.addErrorMessage(e.getDefaultMessage()));
			response.setBody(converter.castToOutput(input));
			return ResponseEntity.badRequest().body(response);
		}
		
		try {
			 output = service.updateCategory(input);
		} catch(Exception e) {
			output = converter.castToOutput(input);
			response.setBody(output);
			
			if(e instanceof CategoryNotFoundException ||
			   e instanceof ValidationException ||
			   e instanceof InvalidDataException) {
				response.addErrorMessage(po.getMessage(e.getMessage()));	
			} else {
				response.addErrorMessage(po.getMessage(GENERIC_INTERNAL_ERROR_MSG));	
			}
			
			return ResponseEntity.badRequest().body(response);	
		}
		
		response.setBody(output);
		
		return ResponseEntity.ok(response);
	}
	
}
