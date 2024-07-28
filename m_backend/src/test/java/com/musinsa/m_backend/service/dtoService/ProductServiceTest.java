package com.musinsa.m_backend.service.dtoService;

import com.musinsa.m_backend.dto.base.ProductDto;
import com.musinsa.m_backend.entity.BrandEntity;
import com.musinsa.m_backend.entity.CategoryEntity;
import com.musinsa.m_backend.entity.ProductEntity;
import com.musinsa.m_backend.mapper.ProductMapper;
import com.musinsa.m_backend.repository.product.ProductRepository;
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

class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductMapper productMapper;

	@Mock
	private BrandEntity brand;

	@Mock
	private CategoryEntity category;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("모든 product 리스트 조회")
	void findAll() {
		List<ProductEntity> entities = List.of(
			ProductEntity.builder().productIdx(1L).productName("Product A").brand(brand).category(category).build(),
			ProductEntity.builder().productIdx(2L).productName("Product B").brand(brand).category(category).build()
		);
		List<ProductDto.Info> dtos = List.of(
			ProductDto.Info.builder().productIdx(1L).productName("Product A").build(),
			ProductDto.Info.builder().productIdx(2L).productName("Product B").build()
		);

		when(productRepository.findAll()).thenReturn(entities);
		when(productMapper.toInfoDto(entities)).thenReturn(dtos);

		List<ProductDto.Info> result = productService.findAll();

		assertEquals(dtos, result);
		verify(productRepository, times(1)).findAll();
		verify(productMapper, times(1)).toInfoDto(entities);
	}

	@Test
	@DisplayName("productIdx로 product 조회")
	void findProductByProductIdx() {
		Long productIdx = 1L;
		ProductEntity entity = ProductEntity.builder().productIdx(productIdx).productName("Product A").brand(brand).category(category).build();
		ProductDto.Info dto = ProductDto.Info.builder().productIdx(productIdx).productName("Product A").build();

		when(productRepository.findProductByProductIdx(productIdx)).thenReturn(Optional.of(entity));
		when(productMapper.toInfoDto(entity)).thenReturn(dto);

		ProductDto.Info result = productService.findProductByProductIdx(productIdx);

		assertEquals(dto, result);
		verify(productRepository, times(1)).findProductByProductIdx(productIdx);
		verify(productMapper, times(1)).toInfoDto(entity);
	}

	@Test
	@DisplayName("productIdx로 product 조회 결과 없음")
	void findProductByProductIdx_notFound() {
		Long productIdx = 1L;

		when(productRepository.findProductByProductIdx(productIdx)).thenReturn(Optional.empty());

		ProductDto.Info result = productService.findProductByProductIdx(productIdx);

		assertNull(result);
		verify(productRepository, times(1)).findProductByProductIdx(productIdx);
		verify(productMapper, times(0)).toInfoDto(any(ProductEntity.class));
	}

	@Test
	@DisplayName("brandIdx로 product 리스트 조회")
	void findProductByBrandIdx() {
		Long brandIdx = 1L;
		List<ProductEntity> entities = List.of(
			ProductEntity.builder().productIdx(1L).brand(brand).category(category).productName("Product A").build(),
			ProductEntity.builder().productIdx(2L).brand(brand).category(category).productName("Product B").build()
		);
		List<ProductDto.Info> dtos = List.of(
			ProductDto.Info.builder().productIdx(1L).brandIdx(brandIdx).productName("Product A").build(),
			ProductDto.Info.builder().productIdx(2L).brandIdx(brandIdx).productName("Product B").build()
		);

		when(productRepository.findProductByBrandIdx(brandIdx)).thenReturn(entities);
		when(productMapper.toInfoDto(entities)).thenReturn(dtos);

		List<ProductDto.Info> result = productService.findProductByBrandIdx(brandIdx);

		assertEquals(dtos, result);
		verify(productRepository, times(1)).findProductByBrandIdx(brandIdx);
		verify(productMapper, times(1)).toInfoDto(entities);
	}

	@Test
	@DisplayName("categoryIdx로 max price product 조회")
	void findMaxPriceProductByCategoryIdx() {
		Long categoryIdx = 1L;
		ProductEntity entity = ProductEntity.builder().productIdx(1L).category(category).productName("Product A").productPrice(1000.0).build();
		ProductDto.Info dto = ProductDto.Info.builder().productIdx(1L).categoryIdx(categoryIdx).productName("Product A").productPrice(1000.0).build();

		when(productRepository.findMaxPriceProductByCategoryIdx(categoryIdx)).thenReturn(Optional.of(entity));
		when(productMapper.toInfoDto(entity)).thenReturn(dto);

		ProductDto.Info result = productService.findMaxPriceProductByCategoryIdx(categoryIdx);

		assertEquals(dto, result);
		verify(productRepository, times(1)).findMaxPriceProductByCategoryIdx(categoryIdx);
		verify(productMapper, times(1)).toInfoDto(entity);
	}

	@Test
	@DisplayName("categoryIdx로 max price product 조회 결과 없음")
	void findMaxPriceProductByCategoryIdx_notFound() {
		Long categoryIdx = 1L;

		when(productRepository.findMaxPriceProductByCategoryIdx(categoryIdx)).thenReturn(Optional.empty());

		ProductDto.Info result = productService.findMaxPriceProductByCategoryIdx(categoryIdx);

		assertNull(result);
		verify(productRepository, times(1)).findMaxPriceProductByCategoryIdx(categoryIdx);
		verify(productMapper, times(0)).toInfoDto(any(ProductEntity.class));
	}

	@Test
	@DisplayName("categoryIdx로 min price product 조회")
	void findMinPriceProductByCategoryIdx() {
		Long categoryIdx = 1L;
		ProductEntity entity = ProductEntity.builder().productIdx(1L).category(category).productName("Product A").productPrice(100.0).build();
		ProductDto.Info dto = ProductDto.Info.builder().productIdx(1L).categoryIdx(categoryIdx).productName("Product A").productPrice(100.0).build();

		when(productRepository.findMinPriceProductByCategoryIdx(categoryIdx)).thenReturn(Optional.of(entity));
		when(productMapper.toInfoDto(entity)).thenReturn(dto);

		ProductDto.Info result = productService.findMinPriceProductByCategoryIdx(categoryIdx);

		assertEquals(dto, result);
		verify(productRepository, times(1)).findMinPriceProductByCategoryIdx(categoryIdx);
		verify(productMapper, times(1)).toInfoDto(entity);
	}

	@Test
	@DisplayName("categoryIdx로 min price product 조회 결과 없음")
	void findMinPriceProductByCategoryIdx_notFound() {
		Long categoryIdx = 1L;

		when(productRepository.findMinPriceProductByCategoryIdx(categoryIdx)).thenReturn(Optional.empty());

		ProductDto.Info result = productService.findMinPriceProductByCategoryIdx(categoryIdx);

		assertNull(result);
		verify(productRepository, times(1)).findMinPriceProductByCategoryIdx(categoryIdx);
		verify(productMapper, times(0)).toInfoDto(any(ProductEntity.class));
	}

	@Test
	@DisplayName("brandIdx와 categoryIdx로 product 존재 여부 확인")
	void existProductByBrandIdxAndCategoryIdx() {
		Long brandIdx = 1L;
		Long categoryIdx = 1L;

		when(productRepository.existsProductEntitiesByBrandIdxAndCategoryIdx(brandIdx, categoryIdx)).thenReturn(true);

		Boolean result = productService.existProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx);

		assertTrue(result);
		verify(productRepository, times(1)).existsProductEntitiesByBrandIdxAndCategoryIdx(brandIdx, categoryIdx);
	}

	@Test
	@DisplayName("brandIdx와 categoryIdx로 min price product 조회")
	void findMinPriceProductByBrandIdxAndCategoryIdx() {
		Long brandIdx = 1L;
		Long categoryIdx = 1L;
		ProductEntity entity = ProductEntity.builder().productIdx(1L).brand(brand).category(category).productName("Product A").productPrice(100.0).build();
		ProductDto.Info dto = ProductDto.Info.builder().productIdx(1L).brandIdx(brandIdx).categoryIdx(categoryIdx).productName("Product A").productPrice(100.0).build();

		when(productRepository.findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx)).thenReturn(Optional.of(entity));
		when(productMapper.toInfoDto(entity)).thenReturn(dto);

		ProductDto.Info result = productService.findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx);

		assertEquals(dto, result);
		verify(productRepository, times(1)).findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx);
		verify(productMapper, times(1)).toInfoDto(entity);
	}

	@Test
	@DisplayName("brandIdx와 categoryIdx로 min price product 조회 결과 없음")
	void findMinPriceProductByBrandIdxAndCategoryIdx_notFound() {
		Long brandIdx = 1L;
		Long categoryIdx = 1L;

		when(productRepository.findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx)).thenReturn(Optional.empty());

		ProductDto.Info result = productService.findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx);

		assertNull(result);
		verify(productRepository, times(1)).findMinPriceProductByBrandIdxAndCategoryIdx(brandIdx, categoryIdx);
		verify(productMapper, times(0)).toInfoDto(any(ProductEntity.class));
	}

	@Test
	@DisplayName("product 저장")
	void save() {
		ProductDto.Info dto = ProductDto.Info.builder().productIdx(1L).productName("Product A").build();
		ProductEntity entity = ProductEntity.builder().productIdx(1L).productName("Product A").brand(brand).category(category).build();
		ProductEntity savedEntity = ProductEntity.builder().productIdx(1L).productName("Product A").brand(brand).category(category).build();
		ProductDto.Info savedDto = ProductDto.Info.builder().productIdx(1L).productName("Product A").build();

		when(productMapper.fromInfoToEntity(dto)).thenReturn(entity);
		when(productRepository.save(entity)).thenReturn(savedEntity);
		when(productMapper.toInfoDto(savedEntity)).thenReturn(savedDto);

		ProductDto.Info result = productService.save(dto);

		assertEquals(savedDto, result);
		verify(productMapper, times(1)).fromInfoToEntity(dto);
		verify(productRepository, times(1)).save(entity);
		verify(productMapper, times(1)).toInfoDto(savedEntity);
	}

	@Test
	@DisplayName("product 삭제")
	void deleteProduct() {
		Long productIdx = 1L;

		doNothing().when(productRepository).deleteProductByProductIdx(productIdx);

		productService.deleteProduct(productIdx);

		verify(productRepository, times(1)).deleteProductByProductIdx(productIdx);
	}
}
