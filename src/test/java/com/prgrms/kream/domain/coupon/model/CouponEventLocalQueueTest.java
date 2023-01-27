package com.prgrms.kream.domain.coupon.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

class CouponEventLocalQueueTest {

	@Test
	void addQueue() {
		//given
		CouponEventRegisterRequest couponEventRegisterRequest =
				new CouponEventRegisterRequest(1L, 1L);

		//when
		CouponEventLocalQueue.addQueue(couponEventRegisterRequest);

		//then
		assertThat(CouponEventLocalQueue.pollQueue()).isEqualTo(couponEventRegisterRequest);
	}

	@Test
	void size() {
		//given when
		for (int i=0; i<10000; i++) {
			CouponEventRegisterRequest couponEventRegisterRequest =
					new CouponEventRegisterRequest(1L, i);
			CouponEventLocalQueue.addQueue(couponEventRegisterRequest);
		}

		//then
		assertThat(CouponEventLocalQueue.size()).isEqualTo(10000);
	}
}