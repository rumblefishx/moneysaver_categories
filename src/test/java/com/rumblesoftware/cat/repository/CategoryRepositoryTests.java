package com.rumblesoftware.cat.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.rumblesoftware.cat.model.CategoryEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategoryRepositoryTests {
	
	
	private static final String VALID_CAT_NAME = "category_name";
	private static final Long VALID_CUSTOMER_ID = 1L; 
	private static final Long VALID_CATEGORY_ID = 1001L; 
	
	@Autowired
	private CategoryRepository repository;

	@Test
	public void catByNameAndUserOKTest() {
		Optional<CategoryEntity> entity = 
				repository.findCategoryByNameAndUser(VALID_CUSTOMER_ID,VALID_CAT_NAME);
		
		assertTrue(entity.isPresent());
	}
	

	@Test
	public void catByIdsOkTest() {
		Optional<CategoryEntity> entity = 
				repository.findCategoryByIds(VALID_CUSTOMER_ID,VALID_CATEGORY_ID);
		
		assertTrue(entity.isPresent());
	}
	
	@Test
	public void catsByCustomerOKTest() {
		Optional<List<CategoryEntity>> lst = 
				repository.findCategoriesByCustomer(VALID_CUSTOMER_ID);
		
		assertTrue(lst.isPresent());
	}
}
