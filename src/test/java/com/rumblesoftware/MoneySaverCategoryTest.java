package com.rumblesoftware;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import com.rumblesoftware.cat.controller.CategoryControllerTests;
import com.rumblesoftware.cat.service.CategoryServiceTests;

@RunWith(Suite.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Suite.SuiteClasses(value = { CategoryServiceTests.class, CategoryControllerTests.class})
public class MoneySaverCategoryTest {

	@Test
	public void contextLoads() {
	}
}
