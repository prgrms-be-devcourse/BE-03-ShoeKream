package com.prgrms.kream.domain.coupon.dto.request;

import com.prgrms.kream.domain.coupon.dto.response.CouponResponse;
import com.prgrms.kream.domain.member.model.Member;

public record CouponEventServiceRequest(
		//TODO merge 후 memberDto로 수정
		Member member,
		CouponResponse couponResponse
) {
}
