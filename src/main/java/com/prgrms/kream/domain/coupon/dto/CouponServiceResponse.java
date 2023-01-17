package com.prgrms.kream.domain.coupon.dto;

public record CouponServiceResponse(
		Long id,
		int discountValue,
		String name,
		int amount
) {

}
