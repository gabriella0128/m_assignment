package com.musinsa.m_backend.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.musinsa.m_backend.dto.CategoryReadDto;
import com.musinsa.m_backend.service.CategoryReadService;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryReadService categoryReadService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("카테고리 리스트 Select 조회")
	void readCategoryListSelect() throws Exception {
		CategoryReadDto.CategoryReadSelectItem item = CategoryReadDto.CategoryReadSelectItem.builder()
			.categoryIdx(1L)
			.categoryName("상의")
			.build();

		List<CategoryReadDto.CategoryReadSelectItem> categorySelectList = List.of(item);

		CategoryReadDto.CategoryReadListSelectResponse response = CategoryReadDto.CategoryReadListSelectResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.categorySelectList(categorySelectList)
			.build();

		when(categoryReadService.readCategorySelectList()).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(get("/api/category/list/select")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

}
