package com.prgrms.kream.domain.coupon.dto;

public record CouponControllerResponse(
		Long id,
		int discountValue,
		String name,
		int amount
) {
}
