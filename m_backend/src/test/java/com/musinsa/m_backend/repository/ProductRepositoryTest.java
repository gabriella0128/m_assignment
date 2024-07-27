package com.musinsa.m_backend.repository;

import java.util.List;
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

import com.musinsa.m_backend.entity.BrandEntity;
import com.musinsa.m_backend.entity.CategoryEntity;
import com.musinsa.m_backend.entity.ProductEntity;
import com.musinsa.m_backend.repository.brand.BrandRepository;
import com.musinsa.m_backend.repository.category.CategoryRepository;
import com.musinsa.m_backend.repository.product.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class ProductRepositoryTest {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	private ProductEntity testEntity;

	@BeforeAll
	void setUp() {
		BrandEntity brandEntity = BrandEntity.builder()
			.brandName("A")
			.brandDesc("Brand A")
			.build();
		BrandEntity brand = brandRepository.save(brandEntity);

		CategoryEntity categoryEntity = CategoryEntity.builder()
			.categoryName("상의")
			.build();
		CategoryEntity category = categoryRepository.save(categoryEntity);

		ProductEntity productEntity = ProductEntity.builder()
			.productName("Product A")
			.productPrice(100.0)
			.productDesc("This is Product A")
			.brand(brand)
			.category(category)
			.build();
		testEntity = productRepository.save(productEntity);

	}

	@AfterAll
	void tearDown() {
		productRepository.deleteAll();
		brandRepository.deleteAll();
		categoryRepository.deleteAll();
	}

	@Test
	@DisplayName("productIdx로 product 정보 조회")
	void testFindProductByProductIdx() {
		Optional<ProductEntity> foundProduct = productRepository.findProductByProductIdx(testEntity.getProductIdx());

		assertTrue(foundProduct.isPresent());
		assertEquals(testEntity.getProductIdx(), foundProduct.get().getProductIdx());
		assertEquals(testEntity.getProductName(), foundProduct.get().getProductName());
		assertEquals(testEntity.getProductDesc(), foundProduct.get().getProductDesc());
		assertEquals(testEntity.getProductPrice(), foundProduct.get().getProductPrice());
		assertEquals(testEntity.getBrand().getBrandIdx(), foundProduct.get().getBrand().getBrandIdx());
		assertEquals(testEntity.getCategory().getCategoryIdx(), foundProduct.get().getCategory().getCategoryIdx());
	}

	@Test
	@DisplayName("brandIdx로 product list 정보 조회")
	void testFindProductByBrandIdx() {
		List<ProductEntity> products = productRepository.findProductByBrandIdx(testEntity.getBrand().getBrandIdx());
		assertFalse(products.isEmpty());
		assertEquals(testEntity.getBrand().getBrandIdx(), products.get(0).getBrand().getBrandIdx());
	}

	@Test
	@DisplayName("categoryIdx로 최고가 상품 정보 조회")
	void testFindMaxPriceProductByCategoryIdx() {
		Optional<ProductEntity> foundProduct = productRepository.findMaxPriceProductByCategoryIdx(testEntity.getCategory().getCategoryIdx());
		assertTrue(foundProduct.isPresent());
		assertEquals(testEntity.getProductPrice(), foundProduct.get().getProductPrice());
	}

	@Test
	@DisplayName("categoryIdx로 최저가 상품 정보 조회")
	void testFindMinPriceProductByCategoryIdx() {
		Optional<ProductEntity> foundProduct = productRepository.findMinPriceProductByCategoryIdx(testEntity.getCategory().getCategoryIdx());
		assertTrue(foundProduct.isPresent());
		assertEquals(testEntity.getProductPrice(), foundProduct.get().getProductPrice());
	}

	@Test
	@DisplayName("brandIdx와 categoryIdx로 product 존재 유무 정보 조회")
	void testExistsProductEntitiesByBrandIdxAndCategoryIdx() {
		boolean exists = productRepository.existsProductEntitiesByBrandIdxAndCategoryIdx(testEntity.getBrand().getBrandIdx(), testEntity.getCategory().getCategoryIdx());
		assertTrue(exists);
	}

	@Test
	@DisplayName("brandIdx와 categoryIdx로 최저가 상품 정보 조회")
	void testFindMinPriceProductByBrandIdxAndCategoryIdx() {
		Optional<ProductEntity> foundProduct = productRepository.findMinPriceProductByBrandIdxAndCategoryIdx(testEntity.getBrand().getBrandIdx(), testEntity.getCategory().getCategoryIdx());
		assertTrue(foundProduct.isPresent());
		assertEquals(testEntity.getProductPrice(), foundProduct.get().getProductPrice());
	}

	@Test
	@DisplayName("product 정보 삭제")
	void testDeleteProductByProductIdx() {
		productRepository.deleteProductByProductIdx(testEntity.getProductIdx());

		Optional<ProductEntity> deletedProduct = productRepository.findDeletedProductByProductIdx(testEntity.getProductIdx());

		assertTrue(deletedProduct.isPresent());
		assertFalse(deletedProduct.get().getUseYn());
		assertNotNull(deletedProduct.get().getDeleteDt());
	}

}
