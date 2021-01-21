package com.rumblesoftware.cat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rumblesoftware.cat.model.CategoryEntity;
import com.rumblesoftware.cat.model.CategoryId;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, CategoryId> {

	@Query(value = "Select c from TCategory c where c.customerId = :customerId and c.categoryName=:categoryName")
	public Optional<CategoryEntity> findCategoryByNameAndUser(
			@Param("customerId") Long customerId,
			@Param("categoryName") String categoryName);
}
