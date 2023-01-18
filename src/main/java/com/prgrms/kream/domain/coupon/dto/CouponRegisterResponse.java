package com.prgrms.kream.domain.coupon.dto;

public record CouponRegisterResponse(
		Long id,
		int discountValue,
		String name,
		int amount
) {
}
