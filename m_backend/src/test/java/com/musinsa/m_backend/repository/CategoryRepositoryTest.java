package com.musinsa.m_backend.repository;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.musinsa.m_backend.entity.CategoryEntity;
import com.musinsa.m_backend.repository.category.CategoryRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	private CategoryEntity testEntity;

	@BeforeAll
	void setup(){
		CategoryEntity categoryEntity = CategoryEntity.builder()
			.categoryName("상의")
			.build();

		testEntity = categoryRepository.save(categoryEntity);
	}

	@AfterAll
	void teardown(){
		categoryRepository.deleteAll();
	}

	@Test
	@DisplayName("categoryIdx로 category 정보 조회")
	void testFindCategoryByCategoryIdx() {
		Optional<CategoryEntity> foundCategory = categoryRepository.findCategoryByCategoryIdx(testEntity.getCategoryIdx());
		assertTrue(foundCategory.isPresent());
		assertEquals("상의", foundCategory.get().getCategoryName());
	}

	@Test
	@DisplayName("categoryName으로 category 정보 조회")
	void testFindCategoryByCategoryName() {
		Optional<CategoryEntity> foundCategory = categoryRepository.findCategoryByCategoryName(testEntity.getCategoryName());
		assertTrue(foundCategory.isPresent());
		assertEquals("상의", foundCategory.get().getCategoryName());
	}


}
