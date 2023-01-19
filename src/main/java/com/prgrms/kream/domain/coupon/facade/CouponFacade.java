package com.prgrms.kream.domain.coupon.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventServiceRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
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

	@Transactional
	public CouponEventResponse applyCouponEvent(CouponEventRegisterRequest couponEventRegisterRequest) {
		Member member = memberService.getMemberById(couponEventRegisterRequest.memberId());
		Coupon coupon = couponService.getCouponById(couponEventRegisterRequest.couponId());

		return couponEventService.registerCouponEvent(
				new CouponEventServiceRequest(member, coupon));
	}
}
