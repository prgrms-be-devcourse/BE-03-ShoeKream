package com.prgrms.kream.domain.coupon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

@SpringBootTest
@AutoConfigureMockMvc
class CouponEventControllerTest extends MysqlTestContainer {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	Jackson2ObjectMapperBuilder objectMapperBuilder;

	@Test
	@DisplayName("쿠폰 발급 테스트")
	void applyCouponEvent() throws Exception {
		//given
		CouponEventRegisterRequest couponEventRegisterRequest = new CouponEventRegisterRequest(1L, 1L);

		//when
		ResultActions resultActions = mockMvc.perform(
				post("/api/v1/coupon")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(
								objectMapperBuilder.build()
										.writeValueAsString(couponEventRegisterRequest)
						)
		);

		//then
		resultActions.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("쿠폰 중복 발급 테스트")
	void overLapApplyCouponEvent() throws Exception {
		//given
		CouponEventRegisterRequest couponEventRegisterRequest = new CouponEventRegisterRequest(1L, 1L);

		//when
		mockMvc.perform(
				post("/api/v1/coupon")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(
								objectMapperBuilder.build()
										.writeValueAsString(couponEventRegisterRequest)
						)
		);
		ResultActions resultActions = mockMvc.perform(
				post("/api/v1/coupon")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(
								objectMapperBuilder.build()
										.writeValueAsString(couponEventRegisterRequest)
						)
		);

		//then
		resultActions.andExpect(status().isBadRequest());
	}

}