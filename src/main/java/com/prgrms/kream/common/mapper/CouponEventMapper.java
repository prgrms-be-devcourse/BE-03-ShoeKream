package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.model.CouponEvent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponEventMapper {

	public static CouponEvent toCouponEvent(CouponEventRegisterRequest couponEventRegisterRequest) {
		return CouponEvent.builder()
				.couponId(couponEventRegisterRequest.couponId())
				.memberId(couponEventRegisterRequest.memberId())
				.build();
	}

	public static CouponEventResponse toCouponEventResponse(CouponEvent couponEvent) {
		return new CouponEventResponse(
				couponEvent.getId(),
				couponEvent.getCouponId(),
				couponEvent.getMemberId()
		);
	}
}
