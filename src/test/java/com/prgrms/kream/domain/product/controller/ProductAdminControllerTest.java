package com.prgrms.kream.domain.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Controller layer를 통해 ")
public class ProductAdminControllerTest extends MysqlTestContainer {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ProductRepository productRepository;

	@BeforeEach
	void insertData() {
		Product product = Product.builder()
				.name("Nike Dunk Low")
				.releasePrice(129000)
				.description("Retro Black")
				.build();

		productRepository.save(product);
	}

	@AfterEach
	void clearData() {
		productRepository.deleteAll();
	}

	@Test
	@Sql("classpath:db/schema.sql")
	@DisplayName("등록 요청을 받아 상품을 등록한다")
	void register() throws Exception {
		//given
		MockMultipartFile mockMultipartFile
				= new MockMultipartFile("images", "test.png", MediaType.IMAGE_PNG_VALUE, "test" .getBytes());

		//when
		ResultActions resultActions = mockMvc.perform(multipart("/api/v1/admin/product")
				.file(mockMultipartFile)
				.param("name", "Nike Zoom Vomero 5 SP")
				.param("releasePrice", "189000")
				.param("description", "Anthracite 2023")
				.param("sizes", "200"));

		//then
		resultActions
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.id").value(2));
	}

	@Test
	@Sql("classpath:db/schema.sql")
	@DisplayName("수정 요청을 받아 상품을 수정한다")
	void update() throws Exception {
		//given
		MockMultipartFile mockMultipartFile
				= new MockMultipartFile(
				"images", "updateTest.png", MediaType.IMAGE_PNG_VALUE, "updateTest" .getBytes());

		MockMultipartHttpServletRequestBuilder mockMultipartHttpServletRequestBuilder =
				MockMvcRequestBuilders.multipart("/api/v1/admin/product");
		mockMultipartHttpServletRequestBuilder
				.with(request -> {
					request.setMethod("PATCH");
					return request;
				});

		//when
		ResultActions resultActions = mockMvc.perform(mockMultipartHttpServletRequestBuilder
				.file(mockMultipartFile)
				.param("id", "1")
				.param("releasePrice", "189000")
				.param("description", "Anthracite 2023")
				.param("sizes", "200"));

		//then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(1));
	}

	@Test
	@Sql("classpath:db/schema.sql")
	@DisplayName("삭제 요청을 받아 상품을 삭제한다")
	void delete() throws Exception {
		//given
		Long productId = 1L;

		//when
		ResultActions resultActions
				= mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/product/{id}", productId));

		//then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").value("삭제되었습니다."));
	}
}
