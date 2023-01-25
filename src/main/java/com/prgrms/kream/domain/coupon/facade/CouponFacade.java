package com.prgrms.kream.domain.coupon.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventServiceRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.dto.response.CouponResponse;
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
		//TODO merge 후 memberDto로 수정
		Member member = memberService.getMember(couponEventRegisterRequest.memberId());
		CouponResponse couponResponse = couponService.getCouponById(couponEventRegisterRequest.couponId());
		couponService.decreaseCouponAmount(couponResponse.id());

		return couponEventService.registerCouponEvent(
				new CouponEventServiceRequest(member, couponResponse)
		);
	}
}
