package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.ProductReadDto;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductReadServiceTest {

	@InjectMocks
	private ProductReadService productReadService;

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
	@DisplayName("모든 product 리스트 조회")
	void readProductList() {
		List<ProductDto.Info> productList = List.of(
			ProductDto.Info.builder().productIdx(1L).productName("Product A").productPrice(1000.0).brandIdx(1L).categoryIdx(1L).build(),
			ProductDto.Info.builder().productIdx(2L).productName("Product B").productPrice(2000.0).brandIdx(2L).categoryIdx(2L).build()
		);
		List<BrandDto.Info> brands = List.of(
			BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").build(),
			BrandDto.Info.builder().brandIdx(2L).brandName("Brand B").build()
		);
		List<CategoryDto.Info> categories = List.of(
			CategoryDto.Info.builder().categoryIdx(1L).categoryName("Category A").build(),
			CategoryDto.Info.builder().categoryIdx(2L).categoryName("Category B").build()
		);

		when(productService.findAll()).thenReturn(productList);
		when(brandService.findBrandByBrandIdx(1L)).thenReturn(brands.get(0));
		when(brandService.findBrandByBrandIdx(2L)).thenReturn(brands.get(1));
		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(categories.get(0));
		when(categoryService.findCategoryByCategoryIdx(2L)).thenReturn(categories.get(1));

		ProductReadDto.ProductReadListResponse result = productReadService.readProductList();

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());
		assertEquals(2, result.getProductList().size());

		verify(productService, times(1)).findAll();
		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(brandService, times(1)).findBrandByBrandIdx(2L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(1L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(2L);
	}

	@Test
	@DisplayName("product 리스트 조회 시 예외 발생")
	void readProductList_exception() {
		when(productService.findAll()).thenThrow(new RuntimeException("Unexpected Error"));

		ProductReadDto.ProductReadListResponse result = productReadService.readProductList();

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(productService, times(1)).findAll();
	}

	@Test
	@DisplayName("productIdx로 product 상세 조회")
	void readProductDetail() {
		Long productIdx = 1L;
		ProductDto.Info foundProduct = ProductDto.Info.builder()
			.productIdx(productIdx)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Product A")
			.productPrice(1000.0)
			.productDesc("Description A")
			.productImage("Image A")
			.build();
		BrandDto.Info brand = BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").build();
		CategoryDto.Info category = CategoryDto.Info.builder().categoryIdx(1L).categoryName("Category A").build();

		when(productService.findProductByProductIdx(productIdx)).thenReturn(foundProduct);
		when(brandService.findBrandByBrandIdx(1L)).thenReturn(brand);
		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(category);

		ProductReadDto.ProductReadDetailResponse result = productReadService.readProductDetail(productIdx);

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());
		assertNotNull(result.getProductDetail());
		assertEquals(productIdx, result.getProductDetail().getProductIdx());

		verify(productService, times(1)).findProductByProductIdx(productIdx);
		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(1L);
	}

	@Test
	@DisplayName("productIdx로 product 상세 조회 시 상품 없음")
	void readProductDetail_notFound() {
		Long productIdx = 1L;

		when(productService.findProductByProductIdx(productIdx)).thenReturn(null);

		ProductReadDto.ProductReadDetailResponse result = productReadService.readProductDetail(productIdx);

		assertFalse(result.getResult());
		assertEquals("Product Not Found", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(productIdx);
		verify(brandService, times(0)).findBrandByBrandIdx(anyLong());
		verify(categoryService, times(0)).findCategoryByCategoryIdx(anyLong());
	}

	@Test
	@DisplayName("productIdx로 product 상세 조회 시 예외 발생")
	void readProductDetail_exception() {
		Long productIdx = 1L;

		when(productService.findProductByProductIdx(productIdx)).thenThrow(new RuntimeException("Unexpected Error"));

		ProductReadDto.ProductReadDetailResponse result = productReadService.readProductDetail(productIdx);

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(productIdx);
	}
}
