package com.prgrms.kream.domain.coupon.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.prgrms.kream.common.exception.OutOfStockException;

class CouponTest {

	Coupon makeCoupon() {
		return Coupon.builder()
				.id(1L)
				.amount(1)
				.name("test")
				.discountValue(100)
				.build();
	}

	@Test
	@DisplayName("쿠폰 감소 테스트")
	void decreaseAmountTest() {
		//given
		Coupon coupon = makeCoupon();

		//when
		coupon.decreaseAmount();

		//then
		assertThat(coupon.getAmount()).isEqualTo(0);
	}

	@Test
	@DisplayName("쿠폰 감소 에러 테스트")
	void decreaseAmountErrorTest() {
		//given
		Coupon coupon = makeCoupon();

		//when
		coupon.decreaseAmount();

		//then
		assertThatThrownBy(coupon::decreaseAmount)
				.isInstanceOf(OutOfStockException.class)
				.hasMessage("쿠폰 수량이 전부 소진되었습니다.");
	}

	@Test
	@DisplayName("쿠폰 수량 상태 확인 : 양수")
	void isSoldOutFalseTest() {
		//given when
		Coupon coupon = makeCoupon();

		//then
		assertThat(coupon.isSoldOut()).isEqualTo(false);
	}

	@Test
	@DisplayName("쿠폰 수량 상태 확인 : 음수")
	void isSoldOutTrueTest() {
		//given
		Coupon coupon = makeCoupon();

		//when
		coupon.decreaseAmount();

		//then
		assertThat(coupon.isSoldOut()).isEqualTo(true);
	}
}