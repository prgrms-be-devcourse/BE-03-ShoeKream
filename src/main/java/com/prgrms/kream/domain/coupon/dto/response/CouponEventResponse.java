package com.prgrms.kream.domain.coupon.dto.response;

import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.member.model.Member;

public record CouponEventResponse(
		Long id,
		Coupon coupon,
		Member member
) {

}
