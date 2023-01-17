package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.coupon.dto.CouponEventControllerResponse;
import com.prgrms.kream.domain.coupon.dto.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.CouponEventServiceResponse;
import com.prgrms.kream.domain.coupon.model.CouponEvent;

public class CouponEventMapper {
	private CouponEventMapper() {
	}

	public static CouponEvent toCouponEvent(CouponEventServiceResponse couponEventServiceResponse) {
		return new CouponEvent(
				couponEventServiceResponse.coupon(),
				couponEventServiceResponse.member()
		);
	}

	public static CouponEventControllerResponse toCouponEventControllerResponse(CouponEvent couponEvent) {
		return new CouponEventControllerResponse(
				couponEvent.getId(),
				couponEvent.getCoupon(),
				couponEvent.getMember()
		);
	}
}
