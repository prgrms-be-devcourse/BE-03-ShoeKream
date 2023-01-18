package com.prgrms.kream.domain.product.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.prgrms.kream.MysqlTestContainer;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductAdminControllerTest extends MysqlTestContainer {

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("등록 요청을 받아 상품을 등록한다")
	void register() throws Exception {
		//given
		MockMultipartFile mockMultipartFile
				= new MockMultipartFile("images", "test.png", MediaType.IMAGE_PNG_VALUE, "images".getBytes());

		RequestBuilder request
				= MockMvcRequestBuilders.multipart("/api/v1/admin/product")
				.file(mockMultipartFile)
				.param("name", "나이키 데이브레이크")
				.param("releasePrice", "100000")
				.param("description", "23년 출시");

		//when
		ResultActions resultActions = mockMvc.perform(request);

		//then
		resultActions.andExpect(status().isCreated());
	}
}
