package com.musinsa.m_backend.repository;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.musinsa.m_backend.entity.BrandEntity;
import com.musinsa.m_backend.repository.brand.BrandRepository;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class BrandRepositoryTest {

	@Autowired
	private BrandRepository brandRepository;

	private BrandEntity testEntity;

	@BeforeAll
	void setup(){
		BrandEntity brandEntity = BrandEntity.builder()
			.brandName("A")
			.brandDesc("Brand A")
			.build();

		testEntity = brandRepository.save(brandEntity);
	}

	@AfterAll
	void teardown(){
		brandRepository.deleteAll();
	}

	@Test
	@DisplayName("brandIdx로 brand 정보 조회")
	void testFindBrandByBrandIdx() {

		Optional<BrandEntity> foundBrand = brandRepository.findBrandByBrandIdx(testEntity.getBrandIdx());

		assertTrue(foundBrand.isPresent());
		assertEquals("A", foundBrand.get().getBrandName());
		assertEquals("Brand A", foundBrand.get().getBrandDesc());
	}

	@Test
	@DisplayName("brand 정보 삭제")
	void testDeleteBrandByBrandIdx() {

		brandRepository.deleteBrandByBrandIdx(testEntity.getBrandIdx());

		Optional<BrandEntity> deletedBrand = brandRepository.findDeletedBrandByBrandIdx(testEntity.getBrandIdx());

		assertTrue(deletedBrand.isPresent());
		assertFalse(deletedBrand.get().getUseYn());
		assertNotNull(deletedBrand.get().getDeleteDt());
	}



}
