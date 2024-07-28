package com.musinsa.m_backend.service;

import com.musinsa.m_backend.dto.BrandReadDto;
import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.service.dtoService.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandReadServiceTest {

	@InjectMocks
	private BrandReadService brandReadService;

	@Mock
	private BrandService brandService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("모든 brand 리스트 조회")
	void readBrandList() {
		List<BrandDto.Info> brandList = List.of(
			BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").brandDesc("Description A").brandImage("Image A").build(),
			BrandDto.Info.builder().brandIdx(2L).brandName("Brand B").brandDesc("Description B").brandImage("Image B").build()
		);

		when(brandService.findAll()).thenReturn(brandList);

		BrandReadDto.BrandReadListResponse result = brandReadService.readBrandList();

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());
		assertEquals(2, result.getBrandList().size());

		verify(brandService, times(1)).findAll();
	}

	@Test
	@DisplayName("brand 리스트 조회 시 예외 발생")
	void readBrandList_exception() {
		when(brandService.findAll()).thenThrow(new RuntimeException("Unexpected Error"));

		BrandReadDto.BrandReadListResponse result = brandReadService.readBrandList();

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(brandService, times(1)).findAll();
	}

	@Test
	@DisplayName("모든 brand 선택 리스트 조회")
	void readBrandSelectList() {
		List<BrandDto.Info> brandSelectList = List.of(
			BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").build(),
			BrandDto.Info.builder().brandIdx(2L).brandName("Brand B").build()
		);

		when(brandService.findAll()).thenReturn(brandSelectList);

		BrandReadDto.BrandReadListSelectResponse result = brandReadService.readBrandSelectList();

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());
		assertEquals(2, result.getBrandSelectList().size());

		verify(brandService, times(1)).findAll();
	}

	@Test
	@DisplayName("brand 선택 리스트 조회 시 예외 발생")
	void readBrandSelectList_exception() {
		when(brandService.findAll()).thenThrow(new RuntimeException("Unexpected Error"));

		BrandReadDto.BrandReadListSelectResponse result = brandReadService.readBrandSelectList();

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(brandService, times(1)).findAll();
	}

	@Test
	@DisplayName("brandIdx로 brand 상세 조회")
	void readBrandDetail() {
		Long brandIdx = 1L;
		BrandDto.Info foundBrand = BrandDto.Info.builder().brandIdx(brandIdx).brandName("Brand A").brandDesc("Description A").brandImage("Image A").build();

		when(brandService.findBrandByBrandIdx(brandIdx)).thenReturn(foundBrand);

		BrandReadDto.BrandReadDetailResponse result = brandReadService.readBrandDetail(brandIdx);

		assertTrue(result.getResult());
		assertEquals("SUCCESS", result.getReason());
		assertNotNull(result.getBrandDetail());
		assertEquals(brandIdx, result.getBrandDetail().getBrandIdx());

		verify(brandService, times(1)).findBrandByBrandIdx(brandIdx);
	}

	@Test
	@DisplayName("brandIdx로 brand 상세 조회 시 브랜드 없음")
	void readBrandDetail_notFound() {
		Long brandIdx = 1L;

		when(brandService.findBrandByBrandIdx(brandIdx)).thenReturn(null);

		BrandReadDto.BrandReadDetailResponse result = brandReadService.readBrandDetail(brandIdx);

		assertFalse(result.getResult());
		assertEquals("Brand Not Found", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(brandIdx);
	}

	@Test
	@DisplayName("brandIdx로 brand 상세 조회 시 예외 발생")
	void readBrandDetail_exception() {
		Long brandIdx = 1L;

		when(brandService.findBrandByBrandIdx(brandIdx)).thenThrow(new RuntimeException("Unexpected Error"));

		BrandReadDto.BrandReadDetailResponse result = brandReadService.readBrandDetail(brandIdx);

		assertFalse(result.getResult());
		assertEquals("Unexpected Error", result.getReason());

		verify(brandService, times(1)).findBrandByBrandIdx(brandIdx);
	}
}
