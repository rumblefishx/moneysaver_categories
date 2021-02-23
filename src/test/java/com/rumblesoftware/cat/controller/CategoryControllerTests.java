package com.rumblesoftware.cat.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumblesoftware.cat.io.IOConverter;
import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
import com.rumblesoftware.cat.io.input.dto.CategoryPatchInputDTO;
import com.rumblesoftware.cat.service.CategoryService;
import com.rumblesoftware.utils.PostOfficer;

@WebMvcTest(CategoryController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "webtest")
@AutoConfigureTestDatabase
public class CategoryControllerTests {
	
	private static final String MSG_ID_CUSTOMER_ID_NULL = "{category.new.input.customerId.null}";
	private static final String MSG_ID_BLANK_NAME = "{category.new.input.categoryName.blank}";
	private static final String MSG_ID_BLANK_DESC = "{category.new.input.categoryDescription.blank}";
	private static final String MSG_ID_RANGE_NAME = "{category.new.input.categoryName.range}";
	private static final String MSG_ID_RANGE_DESC = "{category.new.input.categoryDescription.range}";
	
	private static final String MSG_UPD_ID_CUSTOMER_ID_NULL = "{category.patch.input.customerId.null}";
	private static final String MSG_UPD_ID_CATEGORY_ID_NULL = "{category.patch.input.categoryId.null}";
	
	private static final String MSG_ID_UPD_OUT_RANGE_NAME = "{category.patch.input.categoryName.range}";
	private static final String MSG_ID_UPD_OUT_RANGE_DESC = "{category.patch.input.categoryDescription.range}";
	
	private static final String VALID_CAT_NAME = "New Category";	
	private static final String VALID_CAT_DESC = "New Category Description";	
	private static final Long VALID_CUST_ID = 1L;
	
	private static final String OUT_RANGE_NAME = StringUtils.repeat("A", 21);
	private static final String OUT_RANGE_DESC = StringUtils.repeat("A", 71);
	
	private static final String EMPTY_FIELD = " ";
	
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private CategoryService service;
	
	@MockBean
	private IOConverter converter;
	
	@MockBean
	private PostOfficer po;
	
	@MockBean
	private RestTemplateBuilder tb; 
	
	@MockBean
	private MessageSource ms;
	
	@Test
	public void addCategoryValOkTest() throws JsonProcessingException, Exception {
		
		   mockMvc.perform(post("/category")
			        .contentType("application/json")
			        .content(mapper.writeValueAsString(getFilledInput())))
			        .andExpect(status().isOk());
	}
	
	@Test
	public void addCategoryNullValuesTest() throws JsonProcessingException, Exception {
		
		CategoryInputDTO input = new CategoryInputDTO();
		input.setCustomerId(null);
		input.setCategoryName(EMPTY_FIELD);
		input.setCategoryDescription(EMPTY_FIELD);
		
		   mockMvc.perform(post("/category")
			        .contentType("application/json")
			        .content(mapper.writeValueAsString(input)))
			        .andExpect(status().isBadRequest())
			        .andExpect(jsonPath("$.errors[*]", 
		        		containsInAnyOrder(MSG_ID_CUSTOMER_ID_NULL
		        				,MSG_ID_BLANK_NAME
		        				,MSG_ID_BLANK_DESC
		        				,MSG_ID_RANGE_NAME
		        				,MSG_ID_RANGE_DESC)));
	}
	
	@Test
	public void addCategoryOverRangeTest() throws JsonProcessingException, Exception {
		
		CategoryInputDTO input = new CategoryInputDTO();
		input.setCustomerId(1L);
		input.setCategoryName(OUT_RANGE_NAME);
		input.setCategoryDescription(OUT_RANGE_DESC);
		
		   mockMvc.perform(post("/category")
			        .contentType("application/json")
			        .content(mapper.writeValueAsString(input)))
			        .andExpect(status().isBadRequest())
			        .andExpect(jsonPath("$.errors[*]", 
		        		containsInAnyOrder(MSG_ID_RANGE_NAME
		        				,MSG_ID_RANGE_DESC)));
	}
	
	@Test
	public void updCategoryOKTest() throws JsonProcessingException, Exception {
		
		   mockMvc.perform(patch("/category")
			        .contentType("application/json")
			        .content(mapper.writeValueAsString(getFilledInputPatch())))
			        .andExpect(status().isOk());
	}
	
	@Test
	public void updCategoryNullValuesTest() throws JsonProcessingException, Exception {
		
		CategoryPatchInputDTO input = getFilledInputPatch();
		input.setCategoryId(null);
		input.setCustomerId(null);
		
		   mockMvc.perform(patch("/category")
			        .contentType("application/json")
			        .content(mapper.writeValueAsString(input)))
			        .andExpect(status().isBadRequest())
			        .andExpect(jsonPath("$.errors[*]", 
		        		containsInAnyOrder(MSG_UPD_ID_CUSTOMER_ID_NULL
		        				,MSG_UPD_ID_CATEGORY_ID_NULL)));
	}
	
	@Test
	public void updCategoryOverRangeTest() throws JsonProcessingException, Exception {
		
		CategoryPatchInputDTO input = getFilledInputPatch();
		input.setCategoryName(OUT_RANGE_NAME);
		input.setCategoryDescription(OUT_RANGE_DESC);
		
		   mockMvc.perform(patch("/category")
			        .contentType("application/json")
			        .content(mapper.writeValueAsString(input)))
			        .andExpect(status().isBadRequest())
			        .andExpect(jsonPath("$.errors[*]", 
		        		containsInAnyOrder(MSG_ID_UPD_OUT_RANGE_NAME
		        				,MSG_ID_UPD_OUT_RANGE_DESC)));
	}
	
	public CategoryInputDTO getFilledInput() {
		CategoryInputDTO input = new CategoryInputDTO();
		input.setCategoryDescription(VALID_CAT_DESC);
		input.setCategoryName(VALID_CAT_NAME);
		input.setCustomerId(VALID_CUST_ID);
		return input;
	}
	
	public CategoryPatchInputDTO getFilledInputPatch() {
		CategoryPatchInputDTO input = new CategoryPatchInputDTO();
		input.setCategoryDescription(VALID_CAT_DESC);
		input.setCategoryName(VALID_CAT_NAME);
		input.setCustomerId(VALID_CUST_ID);
		input.setCategoryId(VALID_CUST_ID);
		return input;
	}
}
