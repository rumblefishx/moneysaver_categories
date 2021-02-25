package com.rumblesoftware.cat.controller;


import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rumblesoftware.cat.exceptions.CategoryNotFoundException;
import com.rumblesoftware.cat.exceptions.CustomerNotFound;
import com.rumblesoftware.cat.exceptions.InternalValidationErrorException;
import com.rumblesoftware.cat.exceptions.ValidationException;
import com.rumblesoftware.cat.io.IOConverter;
import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
import com.rumblesoftware.cat.io.input.dto.CategoryPatchInputDTO;
import com.rumblesoftware.cat.io.output.dto.CategoriesResponse;
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
	
	private static final String UPD_PHASE = "update";
	
	private static final String CRT_PHASE = "New category phase";
	
	private static final String CAT_NOT_FIND_ERROR = "category.or.user.not.found.message";
	
	
	@RequestMapping(method = RequestMethod.POST,value = "/category")
	public ResponseEntity<CategoryResponse> addNewCategory(@RequestBody @Valid CategoryInputDTO request,BindingResult br){
		CategoryResponse out = new CategoryResponse(); 
		CategoryOutputDTO output = null;
		
		log.debug("[Controller Layer] (New category phase) receiving request...");
		
		if(br.hasErrors()) {
			log.debug("[Controller Layer] (New category phase) delivering validation errors...");
			br.getAllErrors().forEach(e -> out.addErrorMessage(e.getDefaultMessage()));
			out.setBody(converter.castToOutput(request));
			return ResponseEntity.badRequest().body(out);
		}
		
		try {
			log.debug("[Controller Layer] (New category phase) Calling service layer...");
			output = service.addNewCategory(request);
			log.debug("[Controller Layer] (New category phase) Leaving service layer...");
		} catch(CustomerNotFound e) {
			log.debug("[Controller Layer] (New category phase) returning an exception");
			return getResponseArtifactForErrors(converter.castToOutput(request),CRT_PHASE,HttpStatus.NOT_FOUND,e);
		} catch(ValidationException e) {
			log.debug("[Controller Layer] (New category phase) returning an exception");
			return getResponseArtifactForErrors(converter.castToOutput(request),CRT_PHASE,HttpStatus.BAD_REQUEST,e);
		} catch(InternalValidationErrorException e) {
			log.debug("[Controller Layer] (New category phase) returning an exception");
			return getResponseArtifactForErrors(converter.castToOutput(request),CRT_PHASE,HttpStatus.INTERNAL_SERVER_ERROR,e);	
		}
		log.debug("[Controller Layer] (New category phase) returning result");
		
		out.setBody(output);
		return ResponseEntity.ok(out);
	}  
	
	@RequestMapping(method = RequestMethod.PATCH,value = "/category")
	public ResponseEntity<CategoryResponse> updateCategory(
			@RequestBody @Valid CategoryPatchInputDTO input,
			BindingResult br){
		log.debug("[Controller Layer] (Update category) receiving request...");
		CategoryResponse response = new CategoryResponse();
		CategoryOutputDTO output = null;
		
		if(br.hasErrors()) {
			log.debug("[Controller Layer] (Update category) delivering validation errors...");
			br.getAllErrors()
			.forEach(e -> response.addErrorMessage(e.getDefaultMessage()));
			response.setBody(converter.castToOutput(input));
			return ResponseEntity.badRequest().body(response);
		}
		
		try {
			 log.debug("[Controller Layer] (Update category) Calling service layer...");
			 output = service.updateCategory(input);
			 log.debug("[Controller Layer] (Update category) Leaving service layer...");
		} catch(CustomerNotFound|CategoryNotFoundException e){	
			log.debug("[Controller Layer] (Update category) returning an exception");
			return getResponseArtifactForErrors(converter.castToOutput(input),UPD_PHASE,HttpStatus.NOT_FOUND,e);
		} catch(ValidationException e) {		
			log.debug("[Controller Layer] (Update category) returning an exception");
			return getResponseArtifactForErrors(converter.castToOutput(input),UPD_PHASE,HttpStatus.BAD_REQUEST,e);
		} catch(InternalValidationErrorException e) {
			log.debug("[Controller Layer] (New category phase) returning an exception");
			return getResponseArtifactForErrors(converter.castToOutput(input),CRT_PHASE,HttpStatus.INTERNAL_SERVER_ERROR,e);	
		}
		
		log.debug("[Controller Layer] (Update category) returning result");
		response.setBody(output);
		
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/category/{catid}/customer/{custid}")
	public ResponseEntity<CategoryResponse> deleteCategory(
			@PathVariable Long custid,
			@PathVariable Long catid){
		
		CategoryResponse response = new CategoryResponse();
		CategoryOutputDTO output = null;
		
		try {
			output = service.removeCategory(custid, catid);
		} catch(CustomerNotFound|CategoryNotFoundException e){	
			log.debug("[Controller Layer] (Update category) returning an exception");
			return getResponseArtifactForErrors(new CategoryOutputDTO(),UPD_PHASE,HttpStatus.NOT_FOUND,e);
		} catch(ValidationException e) {		
			log.debug("[Controller Layer] (Update category) returning an exception");
			return getResponseArtifactForErrors(new CategoryOutputDTO(),UPD_PHASE,HttpStatus.BAD_REQUEST,e);
		} catch(InternalValidationErrorException e) {
			log.debug("[Controller Layer] (New category phase) returning an exception");
			return getResponseArtifactForErrors(new CategoryOutputDTO(),CRT_PHASE,HttpStatus.INTERNAL_SERVER_ERROR,e);	
		}
		
		response.setBody(output);
		
		return null;
	}
	
	@RequestMapping(method=RequestMethod.GET,value = "/category/{catid}/customer/{custid}")
	public ResponseEntity<CategoryResponse> findCategoryById(
			@PathVariable("catid") Long catid,
			@PathVariable("custid") Long custid){
		CategoryResponse response = new CategoryResponse();
		
		log.debug("[Controller Layer] (Find category by ids) receiving request...");
		
		try {
			log.debug("[Controller Layer] (Find category by ids) Calling service layer...");
			response.setBody(service.findCategoryById(custid, catid));
		} catch(CategoryNotFoundException e) {
			log.debug("[Controller Layer] (Find category by ids) Delivering exception in the response body");
			e.printStackTrace();
			response.setBody(new CategoryOutputDTO());
			response.addErrorMessage(po.getMessage(CAT_NOT_FIND_ERROR));	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}		
		
		log.debug("[Controller Layer] (Find category by ids) Returning result...");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/category/customer/{customerId}",method=RequestMethod.GET)
	private ResponseEntity<CategoriesResponse> getUserCategories(@PathVariable Long customerId){
		CategoriesResponse response = new CategoriesResponse();
		
		log.debug("[Controller Layer] (Find categories by user id) receiving request...");
		try {
			log.debug("[Controller Layer] (Find categories by user id) calling service layer...");
			response.setBodyItems(service.getAllCustomerCat(customerId));
			log.debug("[Controller Layer] (Find categories by user id) leaving service layer...");
		} catch(CategoryNotFoundException e) {
			log.debug("[Controller Layer] (Find categories by user id) delivering an exception as result...");
			e.printStackTrace();				
			response.addError(po.getMessage(CAT_NOT_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		log.debug("[Controller Layer] (Find categories by user id) delivering results...");
		return ResponseEntity.ok(response);
	}
	
	private ResponseEntity<CategoryResponse> getResponseArtifactForErrors(CategoryOutputDTO body,String phase,HttpStatus status, Exception e) {
		CategoryResponse response = new CategoryResponse();
		
		e.printStackTrace();				
		response.setBody(body);
		response.addErrorMessage(po.getMessage(e.getMessage()));
		return ResponseEntity.status(status).body(response);
	}
	
}
