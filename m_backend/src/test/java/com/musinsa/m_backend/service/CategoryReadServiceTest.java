package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.CategoryReadDto;
import com.musinsa.m_backend.dto.base.CategoryDto;
import com.musinsa.m_backend.service.dtoService.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryReadServiceTest {

	@InjectMocks
	private CategoryReadService categoryReadService;

	@Mock
	private CategoryService categoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("모든 category 선택 리스트 조회")
	void readCategorySelectList() {
		List<CategoryDto.Info> categorySelectList = List.of(
			CategoryDto.Info.builder().categoryIdx(1L).categoryName("Category A").build(),
			CategoryDto.Info.builder().categoryIdx(2L).categoryName("Category B").build()
		);

		when(categoryService.findAll()).thenReturn(categorySelectList);

		CategoryReadDto.CategoryReadListSelectResponse result = categoryReadService.readCategorySelectList();

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());
		assertEquals(2, result.getCategorySelectList().size());

		verify(categoryService, times(1)).findAll();
	}

	@Test
	@DisplayName("category 선택 리스트 조회 시 예외 발생")
	void readCategorySelectList_exception() {
		when(categoryService.findAll()).thenThrow(new RuntimeException("Unexpected Error"));

		CategoryReadDto.CategoryReadListSelectResponse result = categoryReadService.readCategorySelectList();

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(categoryService, times(1)).findAll();
	}
}
