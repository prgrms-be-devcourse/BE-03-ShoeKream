package com.prgrms.kream.domain.coupon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.common.config.CouponProperties;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

@SpringBootTest
@AutoConfigureMockMvc
class CouponEventControllerTest extends MysqlTestContainer {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	Jackson2ObjectMapperBuilder objectMapperBuilder;

	@BeforeEach
	void addData() {
		ReflectionTestUtils.setField(CouponProperties.class, "throughput", 100L);
		ReflectionTestUtils.setField(CouponProperties.class, "key", "couponTest");
	}

	@Test
	@Transactional
	@DisplayName("쿠폰 발급 테스트")
	void registerCouponEventTest() throws Exception {
		//given
		CouponEventRegisterRequest couponEventRegisterRequest = new CouponEventRegisterRequest(1L, 1L);

		//when
		ResultActions resultActions = mockMvc.perform(
				post("/api/v1/coupons")
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
	@Transactional
	@DisplayName("쿠폰 중복 신청시 덮어쓰기 테스트")
	void overLapRegisterCouponEventTest() throws Exception {
		//given
		CouponEventRegisterRequest couponEventRegisterRequest = new CouponEventRegisterRequest(1L, 1L);

		//when
		mockMvc.perform(
				post("/api/v1/coupons")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding(StandardCharsets.UTF_8)
						.content(
								objectMapperBuilder.build()
										.writeValueAsString(couponEventRegisterRequest)
						)
		);
		ResultActions resultActions = mockMvc.perform(
				post("/api/v1/coupons")
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

}