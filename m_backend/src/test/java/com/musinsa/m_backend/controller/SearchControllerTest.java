package com.musinsa.m_backend.controller;

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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.m_backend.dto.SearchDto;
import com.musinsa.m_backend.service.SearchService;

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SearchService searchService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("단일 브랜드 최저가 코디 조회")
	void getCheapestCodiBrand() throws Exception {
		SearchDto.SearchCheapestCodiBrandResponse response = SearchDto.SearchCheapestCodiBrandResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.brandIdx(1L)
			.brandName("Brand A")
			.totalPrice(100.0)
			.items(List.of(SearchDto.CheapestCodiBrandItem.builder()
				.categoryIdx(1L)
				.categoryName("Category A")
				.productIdx(1L)
				.productName("Product A")
				.productDesc("Description A")
				.productPrice(100.0)
				.build()))
			.build();

		when(searchService.searchCheapestCodiBrand()).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(get("/api/search/cheapestCodiBrand")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("카테고리별 최저가 상품 조회")
	void getCheapestProductsPerCategory() throws Exception {
		SearchDto.SearchCheapestProductsPerCategoryResponse response = SearchDto.SearchCheapestProductsPerCategoryResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.totalPrice(100.0)
			.items(List.of(SearchDto.CheapestProductsPerCategoryItem.builder()
				.categoryIdx(1L)
				.categoryName("Category A")
				.brandIdx(1L)
				.brandName("Brand A")
				.productIdx(1L)
				.productName("Product A")
				.productPrice(100.0)
				.build()))
			.build();

		when(searchService.searchCheapestProductsPerCategory()).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(get("/api/search/cheapestProductsPerCategory")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("카테고리 기반 최저가, 최고가 상품 검색 조회")
	void getLowestAndHighestPriceProductsForCategory() throws Exception {
		SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse response = SearchDto.SearchHighestAndLowestPriceProductsForCategoryResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.categoryIdx(1L)
			.categoryName("Category A")
			.highestPriceItem(SearchDto.HighestOrLowestPriceItem.builder()
				.brandIdx(1L)
				.brandName("Brand A")
				.productIdx(1L)
				.productName("Product A")
				.productPrice(200.0)
				.build())
			.lowestPriceItem(SearchDto.HighestOrLowestPriceItem.builder()
				.brandIdx(2L)
				.brandName("Brand B")
				.productIdx(2L)
				.productName("Product B")
				.productPrice(50.0)
				.build())
			.build();

		when(searchService.searchHighestAndLowestPriceProductsForCategory("Category A")).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(get("/api/search/lowestAndHighestPricesForACategory")
			.param("categoryName", "Category A")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}
}
