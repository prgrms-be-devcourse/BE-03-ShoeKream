package com.prgrms.kream.common.mapper;

import static com.prgrms.kream.common.mapper.CouponMapper.*;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventServiceRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.model.CouponEvent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponEventMapper {

	public static CouponEvent toCouponEvent(CouponEventServiceRequest couponEventServiceRequest) {
		return CouponEvent.builder()
				.coupon(toCoupon(couponEventServiceRequest.couponResponse()))
				//TODO merge 후 memberDto 로 변경
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
