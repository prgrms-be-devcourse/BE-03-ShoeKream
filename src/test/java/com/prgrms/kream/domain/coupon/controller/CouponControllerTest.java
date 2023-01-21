package com.prgrms.kream.domain.coupon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.coupon.dto.request.CouponRegisterRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class CouponControllerTest extends MysqlTestContainer {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	Jackson2ObjectMapperBuilder objectMapperBuilder;

	@Test
	@DisplayName("쿠폰 생성 테스트")
	void couponRegisterTest() throws Exception {
		//given
		CouponRegisterRequest couponRegisterRequest = new CouponRegisterRequest(100, "테스트 쿠폰", 10000);

		//when
		ResultActions resultActions = mockMvc.perform(
				post("/api/v1/admin/coupon")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(
								objectMapperBuilder.build()
										.writeValueAsString(couponRegisterRequest)
						)
		);

		//then
		resultActions.andExpect(status().isCreated());

	}

}