package com.prgrms.kream.domain.coupon.service;

import static com.prgrms.kream.common.mapper.CouponEventMapper.*;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.coupon.dto.CouponEventControllerResponse;
import com.prgrms.kream.domain.coupon.dto.CouponEventServiceResponse;
import com.prgrms.kream.domain.coupon.model.CouponEvent;
import com.prgrms.kream.domain.coupon.repository.CouponEventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponEventService {
	private final CouponEventRepository couponEventRepository;

	public CouponEventControllerResponse registerCouponEvent(CouponEventServiceResponse couponEventServiceResponse) {
		CouponEvent savedCouponEvent = couponEventRepository.save(
				toCouponEvent(couponEventServiceResponse)
		);

		return toCouponEventControllerResponse(savedCouponEvent);
	}
}
