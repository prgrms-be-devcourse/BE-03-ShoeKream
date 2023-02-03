package com.prgrms.kream.domain.coupon.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.prgrms.kream.common.config.CouponProperties;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.model.CouponEventLocalQueue;

@SpringBootTest
class CouponEventRedisServiceTest {
	@Autowired
	CouponEventRedisService couponEventRedisService;

	@BeforeEach
	void registerData() {
	ReflectionTestUtils.setField(CouponProperties.class, "throughput", 100L);
	ReflectionTestUtils.setField(CouponProperties.class, "key", "couponTest");
		for (long i=0; i<10000; i++) {
			CouponEventRegisterRequest couponEventRegisterRequest =
					new CouponEventRegisterRequest(1L, i);
			couponEventRedisService.registerCouponEventToRedis(couponEventRegisterRequest);
		}
	}

	@AfterEach
	void deleteData() {
		couponEventRedisService.deleteAllCouponEvent(CouponProperties.getKey());
	}

	@Test
	@DisplayName("Redis 추가 테스트")
	void registerCouponEventToRedisTest() {
		//given

		//when

		//then
		assertThat(couponEventRedisService.getRedisSize(CouponProperties.getKey())).isEqualTo(10000);
	}

	@Test
	@DisplayName("Local Queue 추가 테스트")
	void registerCouponEventToLocalQueue() {
		//given when
		couponEventRedisService.registerCouponEventToLocalQueue(CouponProperties.getKey());

		//then
		assertThat(CouponEventLocalQueue.size()).isEqualTo(15L);
	}
}