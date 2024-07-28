package com.musinsa.m_backend.service.dtoService;

import com.musinsa.m_backend.dto.base.CategoryDto;
import com.musinsa.m_backend.entity.CategoryEntity;
import com.musinsa.m_backend.mapper.CategoryMapper;
import com.musinsa.m_backend.repository.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

	@InjectMocks
	private CategoryService categoryService;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private CategoryMapper categoryMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("모든 category 리스트 조회")
	void findAll() {
		List<CategoryEntity> entities = List.of(
			CategoryEntity.builder().categoryIdx(1L).categoryName("Category A").build(),
			CategoryEntity.builder().categoryIdx(2L).categoryName("Category B").build()
		);
		List<CategoryDto.Info> dtos = List.of(
			CategoryDto.Info.builder().categoryIdx(1L).categoryName("Category A").build(),
			CategoryDto.Info.builder().categoryIdx(2L).categoryName("Category B").build()
		);

		when(categoryRepository.findAll()).thenReturn(entities);
		when(categoryMapper.toInfoDto(entities)).thenReturn(dtos);

		List<CategoryDto.Info> result = categoryService.findAll();

		assertEquals(dtos, result);
		verify(categoryRepository, times(1)).findAll();
		verify(categoryMapper, times(1)).toInfoDto(entities);
	}

	@Test
	@DisplayName("categoryIdx로 category 조회")
	void findCategoryByCategoryIdx() {
		Long categoryIdx = 1L;
		CategoryEntity entity = CategoryEntity.builder().categoryIdx(categoryIdx).categoryName("Category A").build();
		CategoryDto.Info dto = CategoryDto.Info.builder().categoryIdx(categoryIdx).categoryName("Category A").build();

		when(categoryRepository.findCategoryByCategoryIdx(categoryIdx)).thenReturn(Optional.of(entity));
		when(categoryMapper.toInfoDto(entity)).thenReturn(dto);

		CategoryDto.Info result = categoryService.findCategoryByCategoryIdx(categoryIdx);

		assertEquals(dto, result);
		verify(categoryRepository, times(1)).findCategoryByCategoryIdx(categoryIdx);
		verify(categoryMapper, times(1)).toInfoDto(entity);
	}

	@Test
	@DisplayName("categoryIdx로 category 조회 결과 없음")
	void findCategoryByCategoryIdx_notFound() {
		Long categoryIdx = 1L;

		when(categoryRepository.findCategoryByCategoryIdx(categoryIdx)).thenReturn(Optional.empty());

		CategoryDto.Info result = categoryService.findCategoryByCategoryIdx(categoryIdx);

		assertNull(result);
		verify(categoryRepository, times(1)).findCategoryByCategoryIdx(categoryIdx);
		verify(categoryMapper, times(0)).toInfoDto(any(CategoryEntity.class));
	}

	@Test
	@DisplayName("categoryName으로 category 조회")
	void findCategoryByCategoryName() {
		String categoryName = "Category A";
		CategoryEntity entity = CategoryEntity.builder().categoryIdx(1L).categoryName(categoryName).build();
		CategoryDto.Info dto = CategoryDto.Info.builder().categoryIdx(1L).categoryName(categoryName).build();

		when(categoryRepository.findCategoryByCategoryName(categoryName)).thenReturn(Optional.of(entity));
		when(categoryMapper.toInfoDto(entity)).thenReturn(dto);

		CategoryDto.Info result = categoryService.findCategoryByCategoryName(categoryName);

		assertEquals(dto, result);
		verify(categoryRepository, times(1)).findCategoryByCategoryName(categoryName);
		verify(categoryMapper, times(1)).toInfoDto(entity);
	}

	@Test
	@DisplayName("categoryName으로 category 조회 결과 없음")
	void findCategoryByCategoryName_notFound() {
		String categoryName = "Category A";

		when(categoryRepository.findCategoryByCategoryName(categoryName)).thenReturn(Optional.empty());

		CategoryDto.Info result = categoryService.findCategoryByCategoryName(categoryName);

		assertNull(result);
		verify(categoryRepository, times(1)).findCategoryByCategoryName(categoryName);
		verify(categoryMapper, times(0)).toInfoDto(any(CategoryEntity.class));
	}
}