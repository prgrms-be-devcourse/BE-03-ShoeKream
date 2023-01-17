package com.prgrms.kream.domain.coupon.dto;

import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.member.model.Member;

public record CouponEventControllerResponse(
		Long id,
		Coupon coupon,
		Member member
) {

}
