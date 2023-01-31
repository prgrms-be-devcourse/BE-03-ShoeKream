package com.prgrms.kream.domain.coupon.service;

import static com.prgrms.kream.common.mapper.CouponEventMapper.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.model.CouponEvent;
import com.prgrms.kream.domain.coupon.repository.CouponEventRepository;
import com.sun.jdi.request.DuplicateRequestException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponEventService {
	private final CouponEventRepository couponEventRepository;

	@Transactional
	public CouponEventResponse registerCouponEvent(CouponEventRegisterRequest couponEventRegisterRequest) {
		if (checkDuplicateApply(couponEventRegisterRequest.memberId(), couponEventRegisterRequest.couponId())) {
			throw new DuplicateRequestException("이미 쿠폰을 발급 받으셨습니다.");
		}
		CouponEvent entity = toCouponEvent(couponEventRegisterRequest);
		CouponEvent savedCouponEvent = couponEventRepository.save(entity);
		return toCouponEventResponse(savedCouponEvent);
	}

	private boolean checkDuplicateApply(long memberId, long couponId) {
		return couponEventRepository.existsByMemberIdAndCouponId(memberId, couponId);
	}
}
