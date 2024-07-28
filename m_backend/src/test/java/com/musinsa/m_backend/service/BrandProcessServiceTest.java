package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.BrandProcessDto;
import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.service.dtoService.BrandService;
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

class BrandProcessServiceTest {

	@InjectMocks
	private BrandProcessService brandProcessService;

	@Mock
	private BrandService brandService;

	@Mock
	private ProductService productService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("브랜드 삽입")
	void insertBrand() {
		BrandProcessDto.BrandUpsertRequest request = BrandProcessDto.BrandUpsertRequest.builder()
			.brandName("Brand A")
			.brandDesc("Description A")
			.brandImage("Image A")
			.build();

		BrandDto.Info brand = BrandDto.Info.builder()
			.brandName(request.getBrandName())
			.brandDesc(request.getBrandDesc())
			.brandImage(request.getBrandImage())
			.build();

		when(brandService.save(any(BrandDto.Info.class))).thenReturn(brand);

		BrandProcessDto.BrandProcessResponse result = brandProcessService.insertBrand(request);

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());

		verify(brandService, times(1)).save(any(BrandDto.Info.class));
	}

	@Test
	@DisplayName("브랜드 삽입 시 예외 발생")
	void insertBrand_exception() {
		BrandProcessDto.BrandUpsertRequest request = BrandProcessDto.BrandUpsertRequest.builder()
			.brandName("Brand A")
			.brandDesc("Description A")
			.brandImage("Image A")
			.build();

		doThrow(new RuntimeException("Unexpected Error")).when(brandService).save(any(BrandDto.Info.class));

		BrandProcessDto.BrandProcessResponse result = brandProcessService.insertBrand(request);

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(brandService, times(1)).save(any(BrandDto.Info.class));
	}

	@Test
	@DisplayName("브랜드 업데이트")
	void updateBrand() {
		BrandProcessDto.BrandUpsertRequest request = BrandProcessDto.BrandUpsertRequest.builder()
			.brandIdx(1L)
			.brandName("Updated Brand A")
			.brandDesc("Updated Description A")
			.brandImage("Updated Image A")
			.build();

		BrandDto.Info foundBrand = BrandDto.Info.builder()
			.brandIdx(request.getBrandIdx())
			.brandName("Brand A")
			.brandDesc("Description A")
			.brandImage("Image A")
			.build();

		when(brandService.findBrandByBrandIdx(request.getBrandIdx())).thenReturn(foundBrand);
		when(brandService.save(any(BrandDto.Info.class))).thenReturn(foundBrand);

		BrandProcessDto.BrandProcessResponse result = brandProcessService.updateBrand(request);

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(request.getBrandIdx());
		verify(brandService, times(1)).save(any(BrandDto.Info.class));
	}

	@Test
	@DisplayName("브랜드 업데이트 시 브랜드 없음")
	void updateBrand_notFound() {
		BrandProcessDto.BrandUpsertRequest request = BrandProcessDto.BrandUpsertRequest.builder()
			.brandIdx(1L)
			.brandName("Updated Brand A")
			.brandDesc("Updated Description A")
			.brandImage("Updated Image A")
			.build();

		when(brandService.findBrandByBrandIdx(request.getBrandIdx())).thenReturn(null);

		BrandProcessDto.BrandProcessResponse result = brandProcessService.updateBrand(request);

		assertFalse(result.getResult());
		assertEquals("Brand Not Found", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(request.getBrandIdx());
		verify(brandService, times(0)).save(any(BrandDto.Info.class));
	}

	@Test
	@DisplayName("브랜드 업데이트 시 예외 발생")
	void updateBrand_exception() {
		BrandProcessDto.BrandUpsertRequest request = BrandProcessDto.BrandUpsertRequest.builder()
			.brandIdx(1L)
			.brandName("Updated Brand A")
			.brandDesc("Updated Description A")
			.brandImage("Updated Image A")
			.build();

		BrandDto.Info foundBrand = BrandDto.Info.builder()
			.brandIdx(request.getBrandIdx())
			.brandName("Brand A")
			.brandDesc("Description A")
			.brandImage("Image A")
			.build();

		when(brandService.findBrandByBrandIdx(request.getBrandIdx())).thenReturn(foundBrand);
		doThrow(new RuntimeException("Unexpected Error")).when(brandService).save(any(BrandDto.Info.class));

		BrandProcessDto.BrandProcessResponse result = brandProcessService.updateBrand(request);

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(request.getBrandIdx());
		verify(brandService, times(1)).save(any(BrandDto.Info.class));
	}

	@Test
	@DisplayName("브랜드 삭제")
	void deleteBrand() {
		Long brandIdx = 1L;

		BrandDto.Info foundBrand = BrandDto.Info.builder()
			.brandIdx(brandIdx)
			.brandName("Brand A")
			.build();

		List<ProductDto.Info> products = List.of(
			ProductDto.Info.builder().productIdx(1L).productName("Product A").build(),
			ProductDto.Info.builder().productIdx(2L).productName("Product B").build()
		);

		when(brandService.findBrandByBrandIdx(brandIdx)).thenReturn(foundBrand);
		when(productService.findProductByBrandIdx(brandIdx)).thenReturn(products);

		BrandProcessDto.BrandProcessResponse result = brandProcessService.deleteBrand(brandIdx);

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(brandIdx);
		verify(productService, times(1)).findProductByBrandIdx(brandIdx);
		verify(productService, times(1)).deleteProduct(1L);
		verify(productService, times(1)).deleteProduct(2L);
		verify(brandService, times(1)).deleteBrand(brandIdx);
	}

	@Test
	@DisplayName("브랜드 삭제 시 브랜드 없음")
	void deleteBrand_notFound() {
		Long brandIdx = 1L;

		when(brandService.findBrandByBrandIdx(brandIdx)).thenReturn(null);

		BrandProcessDto.BrandProcessResponse result = brandProcessService.deleteBrand(brandIdx);

		assertFalse(result.getResult());
		assertEquals("Brand Not Found", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(brandIdx);
		verify(productService, times(0)).findProductByBrandIdx(anyLong());
		verify(productService, times(0)).deleteProduct(anyLong());
		verify(brandService, times(0)).deleteBrand(anyLong());
	}

	@Test
	@DisplayName("브랜드 삭제 시 예외 발생")
	void deleteBrand_exception() {
		Long brandIdx = 1L;

		BrandDto.Info foundBrand = BrandDto.Info.builder()
			.brandIdx(brandIdx)
			.brandName("Brand A")
			.build();

		when(brandService.findBrandByBrandIdx(brandIdx)).thenReturn(foundBrand);
		doThrow(new RuntimeException("Unexpected Error")).when(productService).findProductByBrandIdx(brandIdx);

		BrandProcessDto.BrandProcessResponse result = brandProcessService.deleteBrand(brandIdx);

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(brandIdx);
		verify(productService, times(1)).findProductByBrandIdx(brandIdx);
	}
}
