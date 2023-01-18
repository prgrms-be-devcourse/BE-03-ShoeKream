package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.coupon.dto.CouponRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.CouponRegisterResponse;
import com.prgrms.kream.domain.coupon.model.Coupon;

public class CouponMapper {
	private CouponMapper() {
	}
	public static Coupon toCoupon(CouponRegisterRequest couponRegisterRequest) {
		return Coupon.builder()
				.discountValue(couponRegisterRequest.discountValue())
				.name(couponRegisterRequest.name())
				.amount(couponRegisterRequest.amount())
				.build();
	}

	public static CouponRegisterResponse toCouponRegisterResponse(Coupon coupon) {
		return new CouponRegisterResponse(
				coupon.getId(),
				coupon.getDiscountValue(),
				coupon.getName(),
				coupon.getAmount()
		);
	}
}
