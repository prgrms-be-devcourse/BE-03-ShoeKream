package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventServiceRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.model.CouponEvent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponEventMapper {

	public static CouponEvent toCouponEvent(CouponEventServiceRequest couponEventServiceRequest) {
		return CouponEvent.builder()
				.coupon(couponEventServiceRequest.coupon())
				.member(couponEventServiceRequest.member())
				.build();
	}

	public static CouponEventResponse toCouponEventResponse(CouponEvent couponEvent) {
		return new CouponEventResponse(
				couponEvent.getId(),
				couponEvent.getCoupon(),
				couponEvent.getMember()
		);
	}
}
