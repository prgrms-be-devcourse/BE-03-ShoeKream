package com.prgrms.kream.domain.coupon.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

class CouponEventLocalQueueTest {

	@Test
	@DisplayName("레디스 큐 입력 테스트")
	void registerQueue() {
		//given
		CouponEventRegisterRequest couponEventRegisterRequest =
				new CouponEventRegisterRequest(1L, 1L);

		//when
		CouponEventLocalQueue.add(couponEventRegisterRequest);

		//then
		assertThat(CouponEventLocalQueue.poll()).isEqualTo(couponEventRegisterRequest);
	}

	@Test
	@DisplayName("레디스 큐 크기 테스트")
	void sizeTest() {
		//given when
		for (long i=0; i<10000; i++) {
			CouponEventRegisterRequest couponEventRegisterRequest =
					new CouponEventRegisterRequest(1L, i);
			CouponEventLocalQueue.add(couponEventRegisterRequest);
		}

		//then
		assertThat(CouponEventLocalQueue.size()).isEqualTo(10000);
	}
}