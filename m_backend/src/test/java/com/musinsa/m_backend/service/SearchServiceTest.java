package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.SearchDto;
import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.dto.base.CategoryDto;
import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.service.dtoService.BrandService;
import com.musinsa.m_backend.service.dtoService.CategoryService;
import com.musinsa.m_backend.service.dtoService.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

	@InjectMocks
	private SearchService searchService;

	@Mock
	private ProductService productService;

	@Mock
	private BrandService brandService;

	@Mock
	private CategoryService categoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("최저가 코디 브랜드 검색")
	void searchCheapestCodiBrand() {
		List<BrandDto.Info> brands = List.of(
			BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").build(),
			BrandDto.Info.builder().brandIdx(2L).brandName("Brand B").build()
		);
		List<CategoryDto.Info> categories = List.of(
			CategoryDto.Info.builder().categoryIdx(1L).categoryName("Category A").build(),
			CategoryDto.Info.builder().categoryIdx(2L).categoryName("Category B").build()
		);

		when(brandService.findAll()).thenReturn(brands);
		when(categoryService.findAll()).thenReturn(categories);

		when(productService.existProductByBrandIdxAndCategoryIdx(1L, 1L)).thenReturn(true);
		when(productService.existProductByBrandIdxAndCategoryIdx(1L, 2L)).thenReturn(true);
		when(productService.existProductByBrandIdxAndCategoryIdx(2L, 1L)).thenReturn(true);
		when(productService.existProductByBrandIdxAndCategoryIdx(2L, 2L)).thenReturn(true);

		when(productService.findMinPriceProductByBrandIdxAndCategoryIdx(1L, 1L)).thenReturn(ProductDto.Info.builder().productIdx(1L).productName("Product A1").productPrice(100.0).categoryIdx(1L).build());
		when(productService.findMinPriceProductByBrandIdxAndCategoryIdx(1L, 2L)).thenReturn(ProductDto.Info.builder().productIdx(2L).productName("Product A2").productPrice(200.0).categoryIdx(2L).build());
		when(productService.findMinPriceProductByBrandIdxAndCategoryIdx(2L, 1L)).thenReturn(ProductDto.Info.builder().productIdx(3L).productName("Product B1").productPrice(300.0).categoryIdx(1L).build());
		when(productService.findMinPriceProductByBrandIdxAndCategoryIdx(2L, 2L)).thenReturn(ProductDto.Info.builder().productIdx(4L).productName("Product B2").productPrice(400.0).categoryIdx(2L).build());

		when(brandService.findBrandByBrandIdx(1L)).thenReturn(brands.get(0));
		when(brandService.findBrandByBrandIdx(2L)).thenReturn(brands.get(1));

		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(categories.get(0));
		when(categoryService.findCategoryByCategoryIdx(2L)).thenReturn(categories.get(1));

		SearchDto.SearchCheapestCodiBrandResponse result = searchService.searchCheapestCodiBrand();

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());
		assertEquals(1L, result.getBrandIdx());
		assertEquals("Brand A", result.getBrandName());
		assertEquals(300.0, result.getTotalPrice());

		verify(brandService, times(1)).findAll();
		verify(categoryService, times(1)).findAll();
		verify(productService, times(1)).existProductByBrandIdxAndCategoryIdx(1L, 1L);
		verify(productService, times(1)).existProductByBrandIdxAndCategoryIdx(1L, 2L);
		verify(productService, times(1)).existProductByBrandIdxAndCategoryIdx(2L, 1L);
		verify(productService, times(1)).existProductByBrandIdxAndCategoryIdx(2L, 2L);
		verify(productService, times(1)).findMinPriceProductByBrandIdxAndCategoryIdx(1L, 1L);
		verify(productService, times(1)).findMinPriceProductByBrandIdxAndCategoryIdx(1L, 2L);
		verify(productService, times(1)).findMinPriceProductByBrandIdxAndCategoryIdx(2L, 1L);
		verify(productService, times(1)).findMinPriceProductByBrandIdxAndCategoryIdx(2L, 2L);
	}

	@Test
	@DisplayName("최저가 코디 브랜드 검색 시 예외 발생")
	void searchCheapestCodiBrand_exception() {
		when(brandService.findAll()).thenThrow(new RuntimeException("Unexpected Error"));

		SearchDto.SearchCheapestCodiBrandResponse result = searchService.searchCheapestCodiBrand();

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(brandService, times(1)).findAll();
		verify(categoryService, times(0)).findAll();
		verify(productService, times(0)).existProductByBrandIdxAndCategoryIdx(anyLong(), anyLong());
		verify(productService, times(0)).findMinPriceProductByBrandIdxAndCategoryIdx(anyLong(), anyLong());
	}

	@Test
	@DisplayName("최저가 카테고리별 상품 검색")
	void searchCheapestProductsPerCategory() {
		List<CategoryDto.Info> categories = List.of(
			CategoryDto.Info.builder().categoryIdx(1L).categoryName("Category A").build(),
			CategoryDto.Info.builder().categoryIdx(2L).categoryName("Category B").build()
		);

		when(categoryService.findAll()).thenReturn(categories);
		when(productService.findMinPriceProductByCategoryIdx(1L)).thenReturn(ProductDto.Info.builder().productIdx(1L).productName("Product A1").productPrice(100.0).categoryIdx(1L).brandIdx(1L).build());
		when(productService.findMinPriceProductByCategoryIdx(2L)).thenReturn(ProductDto.Info.builder().productIdx(2L).productName("Product B1").productPrice(200.0).categoryIdx(2L).brandIdx(2L).build());
		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(CategoryDto.Info.builder().categoryIdx(1L).categoryName("Category A").build());
		when(categoryService.findCategoryByCategoryIdx(2L)).thenReturn(CategoryDto.Info.builder().categoryIdx(2L).categoryName("Category B").build());
		when(brandService.findBrandByBrandIdx(1L)).thenReturn(BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").build());
		when(brandService.findBrandByBrandIdx(2L)).thenReturn(BrandDto.Info.builder().brandIdx(2L).brandName("Brand B").build());

		SearchDto.SearchCheapestProductsPerCategoryResponse result = searchService.searchCheapestProductsPerCategory();

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());
		assertEquals(2, result.getItems().size());
		assertEquals(300.0, result.getTotalPrice());

		verify(categoryService, times(1)).findAll();
		verify(productService, times(1)).findMinPriceProductByCategoryIdx(1L);
		verify(productService, times(1)).findMinPriceProductByCategoryIdx(2L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(1L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(2L);
		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(brandService, times(1)).findBrandByBrandIdx(2L);
	}

	@Test
	@DisplayName("최저가 카테고리별 상품 검색 시 예외 발생")
	void searchCheapestProductsPerCategory_exception() {
		when(categoryService.findAll()).thenThrow(new RuntimeException("Unexpected Error"));

		SearchDto.SearchCheapestProductsPerCategoryResponse result = searchService.searchCheapestProductsPerCategory();

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(categoryService, times(1)).findAll();
		verify(productService, times(0)).findMinPriceProductByCategoryIdx(anyLong());
		verify(brandService, times(0)).findBrandByBrandIdx(anyLong());
	}

	@Test
	@DisplayName("카테고리별 최고가와 최저가 상품 검색")
	void searchHighestAndLowestPriceProductsForCategory() {
		String categoryName = "Category A";
		CategoryDto.Info category = CategoryDto.Info.builder().categoryIdx(1L).categoryName(categoryName).build();
		when(categoryService.findCategoryByCategoryName(categoryName)).thenReturn(category);
		when(productService.findMinPriceProductByCategoryIdx(1L)).thenReturn(ProductDto.Info.builder().productIdx(1L).productName("Product A1").productPrice(100.0).brandIdx(1L).build());
		when(productService.findMaxPriceProductByCategoryIdx(1L)).thenReturn(ProductDto.Info.builder().productIdx(2L).productName("Product A2").productPrice(200.0).brandIdx(2L).build());
		when(brandService.findBrandByBrandIdx(1L)).thenReturn(BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").build());
		when(brandService.findBrandByBrandIdx(2L)).thenReturn(BrandDto.Info.builder().brandIdx(2L).brandName("Brand B").build());

		SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse result = searchService.searchHighestAndLowestPriceProductsForCategory(categoryName);

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());
		assertEquals(1L, result.getCategoryIdx());
		assertEquals(categoryName, result.getCategoryName());
		assertEquals(1L, result.getLowestPriceItem().getBrandIdx());
		assertEquals(2L, result.getHighestPriceItem().getBrandIdx());
		assertEquals(1L, result.getLowestPriceItem().getProductIdx());
		assertEquals(2L, result.getHighestPriceItem().getProductIdx());

		verify(categoryService, times(1)).findCategoryByCategoryName(categoryName);
		verify(productService, times(1)).findMinPriceProductByCategoryIdx(1L);
		verify(productService, times(1)).findMaxPriceProductByCategoryIdx(1L);
		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(brandService, times(1)).findBrandByBrandIdx(2L);
	}

	@Test
	@DisplayName("카테고리별 최고가와 최저가 상품 검색 시 카테고리 없음")
	void searchHighestAndLowestPriceProductsForCategory_noCategoryFound() {
		String categoryName = "Category A";
		when(categoryService.findCategoryByCategoryName(categoryName)).thenReturn(null);

		SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse result = searchService.searchHighestAndLowestPriceProductsForCategory(categoryName);

		assertFalse(result.getResult());
		assertEquals("No Matching Category", result.getReason());

		verify(categoryService, times(1)).findCategoryByCategoryName(categoryName);
		verify(productService, times(0)).findMinPriceProductByCategoryIdx(anyLong());
		verify(productService, times(0)).findMaxPriceProductByCategoryIdx(anyLong());
		verify(brandService, times(0)).findBrandByBrandIdx(anyLong());
	}

	@Test
	@DisplayName("카테고리별 최고가와 최저가 상품 검색 시 상품 없음")
	void searchHighestAndLowestPriceProductsForCategory_noProductsFound() {
		String categoryName = "Category A";
		CategoryDto.Info category = CategoryDto.Info.builder().categoryIdx(1L).categoryName(categoryName).build();
		when(categoryService.findCategoryByCategoryName(categoryName)).thenReturn(category);
		when(productService.findMinPriceProductByCategoryIdx(1L)).thenReturn(null);
		when(productService.findMaxPriceProductByCategoryIdx(1L)).thenReturn(null);

		SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse result = searchService.searchHighestAndLowestPriceProductsForCategory(categoryName);

		assertFalse(result.getResult());
		assertEquals("No Product Found", result.getReason());

		verify(categoryService, times(1)).findCategoryByCategoryName(categoryName);
		verify(productService, times(1)).findMinPriceProductByCategoryIdx(1L);
		verify(productService, times(1)).findMaxPriceProductByCategoryIdx(1L);
		verify(brandService, times(0)).findBrandByBrandIdx(anyLong());
	}
}
