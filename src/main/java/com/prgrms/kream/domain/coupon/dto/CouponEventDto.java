package com.prgrms.kream.domain.coupon.dto;

import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.member.model.Member;

public record CouponEventDto(
		Long id,
		Coupon coupon,
		Member member
) {

}
