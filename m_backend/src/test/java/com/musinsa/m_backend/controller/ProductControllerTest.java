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
import com.musinsa.m_backend.dto.ProductProcessDto;
import com.musinsa.m_backend.dto.ProductReadDto;
import com.musinsa.m_backend.service.ProductProcessService;
import com.musinsa.m_backend.service.ProductReadService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductProcessService productProcessService;

	@MockBean
	private ProductReadService productReadService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("상품 리스트 조회")
	void readProductList() throws Exception {
		ProductReadDto.ProductReadListItem item = ProductReadDto.ProductReadListItem.builder()
			.productIdx(1L)
			.productName("Product A")
			.productPrice(100.0)
			.brandName("Brand A")
			.categoryName("Category A")
			.build();

		List<ProductReadDto.ProductReadListItem> productList = List.of(item);

		ProductReadDto.ProductReadListResponse response = ProductReadDto.ProductReadListResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.productList(productList)
			.build();

		when(productReadService.readProductList()).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(get("/api/product/list")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("상품 상세 조회")
	void readProductDetail() throws Exception {
		ProductReadDto.ProductReadDetailItem detailItem = ProductReadDto.ProductReadDetailItem.builder()
			.productIdx(1L)
			.productName("Product A")
			.productPrice(100.0)
			.productDesc("Description A")
			.brandIdx(1L)
			.brandName("Brand A")
			.categoryIdx(1L)
			.categoryName("Category A")
			.build();

		ProductReadDto.ProductReadDetailResponse response = ProductReadDto.ProductReadDetailResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.productDetail(detailItem)
			.build();

		when(productReadService.readProductDetail(1L)).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(get("/api/product/detail")
			.param("productIdx", "1")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("상품 생성")
	void createProduct() throws Exception {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(100.0)
			.brandIdx(1L)
			.categoryIdx(1L)
			.build();

		ProductProcessDto.ProductProcessResponse response = ProductProcessDto.ProductProcessResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.build();

		when(productProcessService.insertProduct(request)).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(post("/api/product/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(request)));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("상품 수정")
	void updateProduct() throws Exception {
		ProductProcessDto.ProductUpsertRequest request = ProductProcessDto.ProductUpsertRequest.builder()
			.productIdx(1L)
			.productName("Product A")
			.productDesc("Description A")
			.productPrice(100.0)
			.brandIdx(1L)
			.categoryIdx(1L)
			.build();

		ProductProcessDto.ProductProcessResponse response = ProductProcessDto.ProductProcessResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.build();

		when(productProcessService.updateProduct(request)).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(patch("/api/product/update")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(request)));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	@DisplayName("상품 삭제")
	void deleteProduct() throws Exception {
		ProductProcessDto.ProductProcessResponse response = ProductProcessDto.ProductProcessResponse.builder()
			.result(true)
			.reason("SUCCESS")
			.build();

		when(productProcessService.deleteProduct(1L)).thenReturn(response);

		ResultActions resultActions = mockMvc.perform(delete("/api/product/delete")
			.param("productIdx", "1")
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
	}


}
