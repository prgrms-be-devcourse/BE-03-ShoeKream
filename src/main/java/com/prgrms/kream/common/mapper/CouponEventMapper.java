package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.coupon.dto.CouponEventDto;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventServiceRequest;
import com.prgrms.kream.domain.coupon.model.CouponEvent;

public class CouponEventMapper {
	private CouponEventMapper() {
	}

	public static CouponEvent toCouponEvent(CouponEventServiceRequest couponEventServiceResponse) {
		return CouponEvent.builder()
				.coupon(couponEventServiceResponse.coupon())
				.member(couponEventServiceResponse.member())
				.build();
	}

	public static CouponEventDto toCouponEventControllerResponse(CouponEvent couponEvent) {
		return new CouponEventDto(
				couponEvent.getId(),
				couponEvent.getCoupon(),
				couponEvent.getMember()
		);
	}
}
