package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.coupon.dto.request.CouponRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponResponse;
import com.prgrms.kream.domain.coupon.model.Coupon;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponMapper {

	public static Coupon toCoupon(CouponRegisterRequest couponRegisterRequest) {
		return Coupon.builder()
				.discountValue(couponRegisterRequest.discountValue())
				.name(couponRegisterRequest.name())
				.amount(couponRegisterRequest.amount())
				.build();
	}

	public static CouponResponse toCouponResponse(Coupon coupon) {
		return new CouponResponse(
				coupon.getId(),
				coupon.getDiscountValue(),
				coupon.getName(),
				coupon.getAmount()
		);
	}
}
