package com.prgrms.kream.domain.coupon.dto.request;

public record CouponEventServiceRequest(
		Long memberId,
		Long couponId
) {
}
