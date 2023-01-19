package com.prgrms.kream.domain.coupon.service;

import static com.prgrms.kream.common.mapper.CouponEventMapper.*;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventServiceRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.model.CouponEvent;
import com.prgrms.kream.domain.coupon.repository.CouponEventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponEventService {
	private final CouponEventRepository couponEventRepository;

	public CouponEventResponse registerCouponEvent(CouponEventServiceRequest couponEventServiceResponse) {
		CouponEvent savedCouponEvent = couponEventRepository.save(
				toCouponEvent(couponEventServiceResponse)
		);

		return toCouponEventResponse(savedCouponEvent);
	}
}
