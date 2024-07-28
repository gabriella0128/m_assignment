package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.ProductProcessDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductProcessServiceTest {

	@InjectMocks
	private ProductProcessService productProcessService;

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
	@DisplayName("상품 삽입")
	void insertProduct() {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(1000.0)
			.productImage("Image A")
			.build();

		BrandDto.Info brand = BrandDto.Info.builder().brandIdx(1L).build();
		CategoryDto.Info category = CategoryDto.Info.builder().categoryIdx(1L).build();

		when(brandService.findBrandByBrandIdx(1L)).thenReturn(brand);
		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(category);

		ProductProcessDto.ProductProcessResponse result = productProcessService.insertProduct(request);

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(1L);
		verify(productService, times(1)).save(any(ProductDto.Info.class));
	}

	@Test
	@DisplayName("상품 삽입 시 브랜드 없음")
	void insertProduct_brandNotFound() {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(1000.0)
			.productImage("Image A")
			.build();

		when(brandService.findBrandByBrandIdx(1L)).thenReturn(null);

		ProductProcessDto.ProductProcessResponse result = productProcessService.insertProduct(request);

		assertFalse(result.getResult());
		assertEquals("Brand Not Found", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(categoryService, times(0)).findCategoryByCategoryIdx(anyLong());
		verify(productService, times(0)).save(any(ProductDto.Info.class));
	}

	@Test
	@DisplayName("상품 삽입 시 카테고리 없음")
	void insertProduct_categoryNotFound() {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(1000.0)
			.productImage("Image A")
			.build();

		BrandDto.Info brand = BrandDto.Info.builder().brandIdx(1L).build();

		when(brandService.findBrandByBrandIdx(1L)).thenReturn(brand);
		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(null);

		ProductProcessDto.ProductProcessResponse result = productProcessService.insertProduct(request);

		assertFalse(result.getResult());
		assertEquals("Category Not Found", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(1L);
		verify(productService, times(0)).save(any(ProductDto.Info.class));
	}

	@Test
	@DisplayName("상품 삽입 시 예외 발생")
	void insertProduct_exception() {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(1000.0)
			.productImage("Image A")
			.build();

		BrandDto.Info brand = BrandDto.Info.builder().brandIdx(1L).build();
		CategoryDto.Info category = CategoryDto.Info.builder().categoryIdx(1L).build();

		when(brandService.findBrandByBrandIdx(1L)).thenReturn(brand);
		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(category);
		doThrow(new RuntimeException("Unexpected Error")).when(productService).save(any(ProductDto.Info.class));

		ProductProcessDto.ProductProcessResponse result = productProcessService.insertProduct(request);

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(1L);
		verify(productService, times(1)).save(any(ProductDto.Info.class));
	}

	@Test
	@DisplayName("상품 업데이트")
	void updateProduct() {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.productIdx(1L)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Updated Product A")
			.productDesc("Updated Description A")
			.productPrice(2000.0)
			.productImage("Updated Image A")
			.build();

		ProductDto.Info foundProduct = ProductDto.Info.builder()
			.productIdx(1L)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(1000.0)
			.productImage("Image A")
			.build();
		BrandDto.Info brand = BrandDto.Info.builder().brandIdx(1L).build();
		CategoryDto.Info category = CategoryDto.Info.builder().categoryIdx(1L).build();

		when(productService.findProductByProductIdx(1L)).thenReturn(foundProduct);
		when(brandService.findBrandByBrandIdx(1L)).thenReturn(brand);
		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(category);

		ProductProcessDto.ProductProcessResponse result = productProcessService.updateProduct(request);

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(1L);
		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(1L);
		verify(productService, times(1)).save(any(ProductDto.Info.class));
	}

	@Test
	@DisplayName("상품 업데이트 시 상품 없음")
	void updateProduct_productNotFound() {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.productIdx(1L)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Updated Product A")
			.productDesc("Updated Description A")
			.productPrice(2000.0)
			.productImage("Updated Image A")
			.build();

		when(productService.findProductByProductIdx(1L)).thenReturn(null);

		ProductProcessDto.ProductProcessResponse result = productProcessService.updateProduct(request);

		assertFalse(result.getResult());
		assertEquals("Product Not Found", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(1L);
		verify(brandService, times(0)).findBrandByBrandIdx(anyLong());
		verify(categoryService, times(0)).findCategoryByCategoryIdx(anyLong());
		verify(productService, times(0)).save(any(ProductDto.Info.class));
	}

	@Test
	@DisplayName("상품 업데이트 시 브랜드 없음")
	void updateProduct_brandNotFound() {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.productIdx(1L)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Updated Product A")
			.productDesc("Updated Description A")
			.productPrice(2000.0)
			.productImage("Updated Image A")
			.build();

		ProductDto.Info foundProduct = ProductDto.Info.builder()
			.productIdx(1L)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(1000.0)
			.productImage("Image A")
			.build();

		when(productService.findProductByProductIdx(1L)).thenReturn(foundProduct);
		when(brandService.findBrandByBrandIdx(1L)).thenReturn(null);

		ProductProcessDto.ProductProcessResponse result = productProcessService.updateProduct(request);

		assertFalse(result.getResult());
		assertEquals("Brand Not Found", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(1L);
		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(categoryService, times(0)).findCategoryByCategoryIdx(anyLong());
		verify(productService, times(0)).save(any(ProductDto.Info.class));
	}

	@Test
	@DisplayName("상품 업데이트 시 카테고리 없음")
	void updateProduct_categoryNotFound() {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.productIdx(1L)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Updated Product A")
			.productDesc("Updated Description A")
			.productPrice(2000.0)
			.productImage("Updated Image A")
			.build();

		ProductDto.Info foundProduct = ProductDto.Info.builder()
			.productIdx(1L)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(1000.0)
			.productImage("Image A")
			.build();
		BrandDto.Info brand = BrandDto.Info.builder().brandIdx(1L).build();

		when(productService.findProductByProductIdx(1L)).thenReturn(foundProduct);
		when(brandService.findBrandByBrandIdx(1L)).thenReturn(brand);
		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(null);

		ProductProcessDto.ProductProcessResponse result = productProcessService.updateProduct(request);

		assertFalse(result.getResult());
		assertEquals("Category Not Found", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(1L);
		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(1L);
		verify(productService, times(0)).save(any(ProductDto.Info.class));
	}

	@Test
	@DisplayName("상품 업데이트 시 예외 발생")
	void updateProduct_exception() {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.productIdx(1L)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Updated Product A")
			.productDesc("Updated Description A")
			.productPrice(2000.0)
			.productImage("Updated Image A")
			.build();

		ProductDto.Info foundProduct = ProductDto.Info.builder()
			.productIdx(1L)
			.brandIdx(1L)
			.categoryIdx(1L)
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(1000.0)
			.productImage("Image A")
			.build();
		BrandDto.Info brand = BrandDto.Info.builder().brandIdx(1L).build();
		CategoryDto.Info category = CategoryDto.Info.builder().categoryIdx(1L).build();

		when(productService.findProductByProductIdx(1L)).thenReturn(foundProduct);
		when(brandService.findBrandByBrandIdx(1L)).thenReturn(brand);
		when(categoryService.findCategoryByCategoryIdx(1L)).thenReturn(category);
		doThrow(new RuntimeException("Unexpected Error")).when(productService).save(any(ProductDto.Info.class));

		ProductProcessDto.ProductProcessResponse result = productProcessService.updateProduct(request);

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(1L);
		verify(brandService, times(1)).findBrandByBrandIdx(1L);
		verify(categoryService, times(1)).findCategoryByCategoryIdx(1L);
		verify(productService, times(1)).save(any(ProductDto.Info.class));
	}

	@Test
	@DisplayName("상품 삭제")
	void deleteProduct() {
		Long productIdx = 1L;

		ProductDto.Info foundProduct = ProductDto.Info.builder()
			.productIdx(productIdx)
			.productName("Product A")
			.build();

		when(productService.findProductByProductIdx(productIdx)).thenReturn(foundProduct);

		ProductProcessDto.ProductProcessResponse result = productProcessService.deleteProduct(productIdx);

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(productIdx);
		verify(productService, times(1)).deleteProduct(productIdx);
	}

	@Test
	@DisplayName("상품 삭제 시 상품 없음")
	void deleteProduct_notFound() {
		Long productIdx = 1L;

		when(productService.findProductByProductIdx(productIdx)).thenReturn(null);

		ProductProcessDto.ProductProcessResponse result = productProcessService.deleteProduct(productIdx);

		assertFalse(result.getResult());
		assertEquals("Product Not Found", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(productIdx);
		verify(productService, times(0)).deleteProduct(anyLong());
	}

	@Test
	@DisplayName("상품 삭제 시 예외 발생")
	void deleteProduct_exception() {
		Long productIdx = 1L;

		ProductDto.Info foundProduct = ProductDto.Info.builder()
			.productIdx(productIdx)
			.productName("Product A")
			.build();

		when(productService.findProductByProductIdx(productIdx)).thenReturn(foundProduct);
		doThrow(new RuntimeException("Unexpected Error")).when(productService).deleteProduct(productIdx);

		ProductProcessDto.ProductProcessResponse result = productProcessService.deleteProduct(productIdx);

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(productService, times(1)).findProductByProductIdx(productIdx);
		verify(productService, times(1)).deleteProduct(productIdx);
	}
}
