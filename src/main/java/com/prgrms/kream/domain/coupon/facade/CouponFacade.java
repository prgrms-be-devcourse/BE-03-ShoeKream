package com.prgrms.kream.domain.coupon.facade;

import org.springframework.stereotype.Component;

import com.prgrms.kream.domain.coupon.dto.CouponEventControllerResponse;
import com.prgrms.kream.domain.coupon.dto.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.CouponEventServiceResponse;
import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.coupon.service.CouponEventService;
import com.prgrms.kream.domain.coupon.service.CouponService;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponFacade {
	private final CouponService couponService;
	private final CouponEventService couponEventService;
	private final MemberService memberService;

	public CouponEventControllerResponse applyCouponEvent(CouponEventRegisterRequest couponEventRegisterRequest) {
		Member member = memberService.getMemberById(couponEventRegisterRequest.member_id());
		Coupon coupon = couponService.getCouponById(couponEventRegisterRequest.coupon_id());

		return couponEventService.registerCouponEvent(
				new CouponEventServiceResponse(member, coupon));
	}
}
