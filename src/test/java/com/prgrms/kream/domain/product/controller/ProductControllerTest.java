package com.prgrms.kream.domain.product.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.product.dto.request.ProductGetAllRequest;
import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Controller layer를 통해 ")
class ProductControllerTest extends MysqlTestContainer {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ProductRepository productRepository;

	@BeforeEach
	void insertData() {
		List<Product> products = List.of(
				Product.builder()
						.name("Nike Dunk Low")
						.releasePrice(129000)
						.description("Retro Black")
						.build(),
				Product.builder()
						.name("Nike Air Force 1 '07")
						.releasePrice(169000)
						.description("WB Flax")
						.build()
		);

		productRepository.saveAll(products);
	}

	@AfterEach
	void clearData() {
		productRepository.deleteAll();
	}

	@Test
	@DisplayName("조회 요청을 받아 상품 상세 정보를 조회한다")
	void get() throws Exception {
		//given
		Long productId = 1L;

		//when
		ResultActions resultActions
				= mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/{id}", productId));

		//then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(1))
				.andExpect(jsonPath("$.data.name").value("Nike Dunk Low"))
				.andExpect(jsonPath("$.data.releasePrice").value(129000))
				.andExpect(jsonPath("$.data.description").value("Retro Black"));
	}

	@Test
	@DisplayName("목록 조회 요청을 받아 상품 목록을 조회한다")
	void getAll() throws Exception {
		//given
		Long cursorId = 3L;
		int pageSize = 2;
		ProductGetAllRequest productGetAllRequest = new ProductGetAllRequest(cursorId, pageSize);

		//when
		ResultActions resultActions
				= mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(productGetAllRequest)));

		//then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.productGetAllResponses[0].id").value(2))
				.andExpect(jsonPath("$.data.productGetAllResponses[0].name").value("Nike Air Force 1 '07"))
				.andExpect(jsonPath("$.data.productGetAllResponses[0].releasePrice").value(169000))
				.andExpect(jsonPath("$.data.productGetAllResponses[0].description").value("WB Flax"))
				.andExpect(jsonPath("$.data.lastId").value(1));
	}
}
