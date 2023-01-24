package com.prgrms.kream.domain.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.prgrms.kream.MysqlTestContainer;
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

	@AfterEach
	void clearDataAfter() {
		productRepository.deleteAll();
	}

	@Test
	@DisplayName("등록 요청을 받아 상품을 등록한다")
	void register() throws Exception {
		//given
		MockMultipartFile mockMultipartFile
				= new MockMultipartFile("images", "test.png", MediaType.IMAGE_PNG_VALUE, "images".getBytes());

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
				.andExpect(jsonPath("$.data.id").value(1));
	}
}
