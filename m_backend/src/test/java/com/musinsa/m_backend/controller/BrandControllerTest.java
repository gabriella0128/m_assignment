package com.musinsa.m_backend.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.m_backend.dto.BrandProcessDto;
import com.musinsa.m_backend.dto.BrandReadDto;
import com.musinsa.m_backend.service.BrandProcessService;
import com.musinsa.m_backend.service.BrandReadService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BrandProcessService brandProcessService;

	@MockBean
	private BrandReadService brandReadService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("브랜드 리스트 조회")
	void readBrandList() throws Exception {
		BrandReadDto.BrandReadItem item = BrandReadDto.BrandReadItem.builder()
			.brandIdx(1L)
			.brandName("A")
			.brandDesc("Brand A")
			.build();

		List<BrandReadDto.BrandReadItem> brandList = List.of(item);

		BrandReadDto.BrandReadListResponse response = BrandReadDto.BrandReadListResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.brandList(brandList)
			.build();

		when(brandReadService.readBrandList()).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(get("/api/brand/list")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));

	}
	@Test
	@DisplayName("브랜드 리스트 Select 조회")
	void readBrandListSelect() throws Exception {
		BrandReadDto.BrandReadSelectItem item = BrandReadDto.BrandReadSelectItem.builder()
			.brandIdx(1L)
			.brandName("A")
			.build();

		List<BrandReadDto.BrandReadSelectItem> brandSelectList = List.of(item);

		BrandReadDto.BrandReadListSelectResponse response = BrandReadDto.BrandReadListSelectResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.brandSelectList(brandSelectList)
			.build();

		when(brandReadService.readBrandSelectList()).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(get("/api/brand/list/select")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("브랜드 상세 조회")
	void readBrandDetail() throws Exception {
		BrandReadDto.BrandReadItem brandDetail = BrandReadDto.BrandReadItem.builder()
			.brandIdx(1L)
			.brandName("A")
			.brandDesc("Brand A")
			.build();

		BrandReadDto.BrandReadDetailResponse response = BrandReadDto.BrandReadDetailResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.brandDetail(brandDetail)
			.build();

		when(brandReadService.readBrandDetail(1L)).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(get("/api/brand/detail")
			.param("brandIdx", "1")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("브랜드 생성")
	void createBrand() throws Exception {
		BrandProcessDto.BrandUpsertRequest request = BrandProcessDto.BrandUpsertRequest.builder()
			.brandName("A")
			.brandDesc("Brand A")
			.build();

		BrandProcessDto.BrandProcessResponse response = BrandProcessDto.BrandProcessResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.build();

		when(brandProcessService.insertBrand(request)).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(post("/api/brand/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(request)));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("브랜드 수정")
	void updateBrand() throws Exception {
		BrandProcessDto.BrandUpsertRequest request = BrandProcessDto.BrandUpsertRequest.builder()
			.brandIdx(1L)
			.brandName("A")
			.brandDesc("Brand A")
			.build();

		BrandProcessDto.BrandProcessResponse response = BrandProcessDto.BrandProcessResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.build();

		when(brandProcessService.updateBrand(request)).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(patch("/api/brand/update")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(request)));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("브랜드 삭제")
	void deleteBrand() throws Exception {
		BrandProcessDto.BrandProcessResponse response = BrandProcessDto.BrandProcessResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.build();

		when(brandProcessService.deleteBrand(1L)).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(delete("/api/brand/delete")
			.param("brandIdx", "1")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}



}
