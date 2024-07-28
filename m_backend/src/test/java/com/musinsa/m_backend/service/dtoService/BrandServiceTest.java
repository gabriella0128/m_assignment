package com.musinsa.m_backend.service.dtoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.musinsa.m_backend.dto.base.BrandDto;
import com.musinsa.m_backend.entity.BrandEntity;
import com.musinsa.m_backend.mapper.BrandMapper;
import com.musinsa.m_backend.repository.brand.BrandRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

class BrandServiceTest {
	@InjectMocks
	private BrandService brandService;

	@Mock
	private BrandRepository brandRepository;

	@Mock
	private BrandMapper brandMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("모든 brand 리스트 조회")
	void findAll() {
		List<BrandEntity> entities = List.of(
			BrandEntity.builder().brandIdx(1L).brandName("Brand A").build(),
			BrandEntity.builder().brandIdx(2L).brandName("Brand B").build()
		);
		List<BrandDto.Info> dtos = List.of(
			BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").build(),
			BrandDto.Info.builder().brandIdx(2L).brandName("Brand B").build()
		);

		when(brandRepository.findAll()).thenReturn(entities);
		when(brandMapper.toInfoDto(entities)).thenReturn(dtos);

		List<BrandDto.Info> result = brandService.findAll();

		assertEquals(dtos, result);
		verify(brandRepository, times(1)).findAll();
		verify(brandMapper, times(1)).toInfoDto(entities);
	}
	@Test
	@DisplayName("brandIdx로 brand 조회")
	void findBrandByBrandIdx() {

		Long brandIdx = 1L;
		BrandEntity entity = BrandEntity.builder().brandIdx(brandIdx).brandName("Brand A").build();
		BrandDto.Info dto = BrandDto.Info.builder().brandIdx(brandIdx).brandName("Brand A").build();

		when(brandRepository.findBrandByBrandIdx(brandIdx)).thenReturn(Optional.of(entity));
		when(brandMapper.toInfoDto(entity)).thenReturn(dto);

		BrandDto.Info result = brandService.findBrandByBrandIdx(brandIdx);

		assertEquals(dto, result);
		verify(brandRepository, times(1)).findBrandByBrandIdx(brandIdx);
		verify(brandMapper, times(1)).toInfoDto(entity);
	}

	@Test
	@DisplayName("brandIdx로 brand 조회 결과 없음")
	void findBrandByBrandIdx_notFound() {

		Long brandIdx = 1L;

		when(brandRepository.findBrandByBrandIdx(brandIdx)).thenReturn(Optional.empty());

		BrandDto.Info result = brandService.findBrandByBrandIdx(brandIdx);

		assertNull(result);
		verify(brandRepository, times(1)).findBrandByBrandIdx(brandIdx);
		verify(brandMapper, times(0)).toInfoDto(any(BrandEntity.class));
	}

	@Test
	@DisplayName("brand 저장")
	void save() {
		BrandDto.Info dto = BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").build();
		BrandEntity entity = BrandEntity.builder().brandIdx(1L).brandName("Brand A").build();
		BrandEntity savedEntity = BrandEntity.builder().brandIdx(1L).brandName("Brand A").build();
		BrandDto.Info savedDto = BrandDto.Info.builder().brandIdx(1L).brandName("Brand A").build();

		when(brandMapper.fromInfoToEntity(dto)).thenReturn(entity);
		when(brandRepository.save(entity)).thenReturn(savedEntity);
		when(brandMapper.toInfoDto(savedEntity)).thenReturn(savedDto);

		BrandDto.Info result = brandService.save(dto);

		assertEquals(savedDto, result);
		verify(brandMapper, times(1)).fromInfoToEntity(dto);
		verify(brandRepository, times(1)).save(entity);
		verify(brandMapper, times(1)).toInfoDto(savedEntity);
	}

	@Test
	@DisplayName("brand 삭제")
	void deleteBrand() {
		Long brandIdx = 1L;

		doNothing().when(brandRepository).deleteBrandByBrandIdx(brandIdx);

		brandService.deleteBrand(brandIdx);

		verify(brandRepository, times(1)).deleteBrandByBrandIdx(brandIdx);
	}
}
