package com.prgrms.kream.domain.coupon.dto.response;

public record CouponDto(
		Long id,
		int discountValue,
		String name,
		int amount
) {
}
