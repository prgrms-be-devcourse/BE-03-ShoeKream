package com.prgrms.kream.domain.coupon.dto.response;

public record CouponResponse(
		Long id,
		int discountValue,
		String name,
		int amount
) {
}
