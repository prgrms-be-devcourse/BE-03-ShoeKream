package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.coupon.dto.CouponControllerResponse;
import com.prgrms.kream.domain.coupon.dto.CouponRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.CouponServiceResponse;
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

	public static CouponServiceResponse toCouponServiceResponse(Coupon coupon) {
		return new CouponServiceResponse(
				coupon.getId(),
				coupon.getDiscountValue(),
				coupon.getName(),
				coupon.getAmount()
		);
	}

	public static CouponControllerResponse toCouponControllerResponse(Coupon coupon) {
		return new CouponControllerResponse(
				coupon.getId(),
				coupon.getDiscountValue(),
				coupon.getName(),
				coupon.getAmount()
		);
	}
}
