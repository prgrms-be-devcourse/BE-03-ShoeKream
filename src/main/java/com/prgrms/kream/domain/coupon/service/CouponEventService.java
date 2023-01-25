package com.prgrms.kream.domain.coupon.service;

import static com.prgrms.kream.common.mapper.CouponEventMapper.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventServiceRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.model.CouponEvent;
import com.prgrms.kream.domain.coupon.repository.CouponEventRepository;
import com.sun.jdi.request.DuplicateRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponEventService {
	private final CouponEventRepository couponEventRepository;

	@Transactional
	public CouponEventResponse registerCouponEvent(CouponEventServiceRequest couponEventServiceRequest) {
		if (!checkOverLapApply(couponEventServiceRequest.memberId(), couponEventServiceRequest.couponId())) {
			CouponEvent entity = toCouponEvent(couponEventServiceRequest);
			CouponEvent savedCouponEvent = couponEventRepository.save(entity);
			return toCouponEventResponse(savedCouponEvent);
		}
		throw new DuplicateRequestException("쿠폰을 중복으로 받을 수 없습니다.");
	}

	private boolean checkOverLapApply(Long memberId, Long couponId) {
		return couponEventRepository.existsByMemberIdAndCouponId(memberId, couponId);
	}
}
