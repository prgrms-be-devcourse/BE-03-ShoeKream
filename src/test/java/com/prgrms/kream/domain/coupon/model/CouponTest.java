package com.prgrms.kream.domain.coupon.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.prgrms.kream.common.exception.OutOfStockException;

class CouponTest {

	@Test
	@DisplayName("쿠폰 감소 테스트")
	void decreaseAmountTest() {
		//given
		Coupon coupon = Coupon.builder()
				.id(1L)
				.amount(1)
				.name("test")
				.discountValue(100)
				.build();

		//when
		coupon.decreaseAmount();

		//then
		assertThat(coupon.getAmount()).isEqualTo(0);
	}

	@Test
	@DisplayName("쿠폰 감소 에러 테스트")
	void decreaseAmountErrorTest() {
		//given
		Coupon coupon = Coupon.builder()
				.id(1L)
				.amount(1)
				.name("test")
				.discountValue(100)
				.build();

		//when
		coupon.decreaseAmount();

		//then
		assertThatThrownBy(coupon::decreaseAmount)
				.isInstanceOf(OutOfStockException.class)
				.hasMessage("쿠폰 수량이 전부 소진되었습니다.");
	}
}