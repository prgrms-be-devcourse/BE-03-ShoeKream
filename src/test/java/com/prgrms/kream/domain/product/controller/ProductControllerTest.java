package com.prgrms.kream.domain.product.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.prgrms.kream.MysqlTestContainer;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest extends MysqlTestContainer {

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("조회 요청을 받아 상품 상세 정보를 조회한다")
	void get() throws Exception {
		//given
		Long id = 1L;

		//when
		ResultActions resultActions
				= mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/{id}", id));

		//then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.name").value("나이키 데이브레이크"))
				.andExpect(jsonPath("$.data.releasePrice").value(100000))
				.andExpect(jsonPath("$.data.description").value("2023년 출시"));
	}
}
