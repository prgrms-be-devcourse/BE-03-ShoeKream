package com.prgrms.kream.domain.coupon.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.model.CouponEventLocalQueue;

@SpringBootTest
class CouponEventRedisServiceTest {
	@Autowired
	CouponEventRedisService couponEventRedisService;

	@BeforeEach
	void addData() {
		for (int i=0; i<10000; i++) {
			CouponEventRegisterRequest couponEventRegisterRequest =
					new CouponEventRegisterRequest(1L, i);
			couponEventRedisService.addRedisQueue(couponEventRegisterRequest);
		}
	}

	@Test
	@DisplayName("Redis Queue 추가 테스트")
	void addRedisQueue() {
		//given
		CouponEventRegisterRequest couponEventRegisterRequest =
				new CouponEventRegisterRequest(1L, 1000);

		//when
		couponEventRedisService.addRedisQueue(couponEventRegisterRequest);

		//then
		assertThat(couponEventRedisService.getRedisQueueSize(1L)).isEqualTo(10000);
	}

	@Test
	@DisplayName("Local Queue 추가 테스트")
	void toLocalQueue() {
		//given when
		couponEventRedisService.toLocalQueue(1L);

		//then
		assertThat(CouponEventLocalQueue.size()).isEqualTo(110);
	}
}