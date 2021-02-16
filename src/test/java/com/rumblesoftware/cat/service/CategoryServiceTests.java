package com.rumblesoftware.cat.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.rumblesoftware.cat.business.impl.UserExistanceValidator;
import com.rumblesoftware.cat.exceptions.CategoryNotFoundException;
import com.rumblesoftware.cat.exceptions.ValidationException;
import com.rumblesoftware.cat.io.input.dto.CategoryInputDTO;
import com.rumblesoftware.cat.io.input.dto.CategoryPatchInputDTO;
import com.rumblesoftware.cat.io.output.dto.CategoryOutputDTO;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTests {
	private static final String SERVICE_RESPONSE = "response";
	private static final String VALID_CAT_DESCRIPTION_NEW = "Category Description Test";
	private static final String VALID_CAT_NAME_NEW = "Category Name";
	private static final Long VALID_CAT_CUSTOMER_ID = 1L;
	
	private static final String UPD_CAT_DESCRIPTION = "Category Description Updated";
	private static final String UPD_CAT_NAME = "Category Updated";
	private static final Long UPD_CAT_CUSTOMER_ID = 1L;
	private static final Long UPD_CAT_ID = 1000L;
	
	private static final Long EXISTANT_CATEGORY_ID = 1000L;
	private static final Long NON_EXISTANT_CATEGORY_ID = 2000L;
	private static final Long EXISTANT_CUSTOMER_ID = 1L;
	private static final Long NON_EXISTANT_CUSTOMER_ID = 2L;
	
	
	
	private static final String VALID_CAT_NAME_DUPL = "name_example";

	@Autowired
	private CategoryService service;

	@Mock
	private RestTemplate restTemplate;
	
	@Autowired
	@InjectMocks
	private UserExistanceValidator userVal;

	@Rule
	public MockitoRule initRule = MockitoJUnit.rule();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createCategoryOkTest() {		
		Mockito.when(restTemplate.getForEntity(Mockito.any(), Mockito.any(), Mockito.anyMap()))
				.thenReturn(ResponseEntity.ok(SERVICE_RESPONSE));

		CategoryOutputDTO output = service.addNewCategory(buildInput());
		assertTrue(output != null && output.getCategoryId() != null);
	}
	
	@Test(expected = ValidationException.class)
	public void createCategoryDuplicatedTest() {		
		Mockito.when(restTemplate.getForEntity(Mockito.any(), Mockito.any(), Mockito.anyMap()))
				.thenReturn(ResponseEntity.ok(SERVICE_RESPONSE));

		CategoryInputDTO input = buildInput();
		input.setCategoryName(VALID_CAT_NAME_DUPL);
		
		service.addNewCategory(input);
	}

	@Test
	public void updateCategoryOKTest() {		
		Mockito.when(restTemplate.getForEntity(Mockito.any(), Mockito.any(), Mockito.anyMap()))
				.thenReturn(ResponseEntity.ok(SERVICE_RESPONSE));
		
		CategoryOutputDTO output = service.updateCategory(buildPatchInput());
		assertTrue(output.getCategoryDescription().equals(UPD_CAT_DESCRIPTION));
		assertTrue(output.getCategoryName().equals(UPD_CAT_NAME));
		assertTrue(output.getCategoryId().equals(UPD_CAT_ID));
		assertTrue(output.getCustomerId().equals(UPD_CAT_CUSTOMER_ID));
	}
	
	@Test
	public void getCategoryOKTest() {
		CategoryOutputDTO output = service.findCategoryById(EXISTANT_CUSTOMER_ID, EXISTANT_CATEGORY_ID);
		assertTrue(output != null && output.getCategoryName() != null);
	}
	
	@Test(expected = CategoryNotFoundException.class)
	public void getCategoryNotFoundTest() {
		CategoryOutputDTO output = service.findCategoryById(EXISTANT_CUSTOMER_ID, NON_EXISTANT_CATEGORY_ID);
		assertTrue(output != null && output.getCategoryName() != null);
	}
	
	@Test
	public void getAllOKTest() {
		List<CategoryOutputDTO> output = service.getAllCustomerCat(EXISTANT_CUSTOMER_ID);
		assertTrue(output.size() > 0);
	}
	
	@Test(expected = CategoryNotFoundException.class)
	public void getAllFailTest() {
		service.getAllCustomerCat(NON_EXISTANT_CUSTOMER_ID);
	}
	
	
	
	private CategoryInputDTO buildInput() {
		CategoryInputDTO input = new CategoryInputDTO();
		input.setCategoryDescription(VALID_CAT_DESCRIPTION_NEW);
		input.setCategoryName(VALID_CAT_NAME_NEW);
		input.setCustomerId(VALID_CAT_CUSTOMER_ID);
		return input;
	}
	
	private CategoryPatchInputDTO buildPatchInput() {
		CategoryPatchInputDTO input = new CategoryPatchInputDTO();
		input.setCategoryId(UPD_CAT_ID);
		input.setCategoryDescription(UPD_CAT_DESCRIPTION);
		input.setCategoryName(UPD_CAT_NAME);
		input.setCustomerId(UPD_CAT_CUSTOMER_ID);
		return input;
	}
}
