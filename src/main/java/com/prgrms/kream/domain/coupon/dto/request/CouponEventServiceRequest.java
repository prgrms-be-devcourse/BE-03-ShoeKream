package com.prgrms.kream.domain.coupon.dto.request;

import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.member.model.Member;

public record CouponEventServiceRequest(
		Member member,
		Coupon coupon
) {
}
