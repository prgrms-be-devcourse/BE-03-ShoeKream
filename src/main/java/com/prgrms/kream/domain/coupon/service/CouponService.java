package com.prgrms.kream.domain.coupon.service;

import static com.prgrms.kream.common.mapper.CouponMapper.*;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.coupon.dto.request.CouponRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponDto;
import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.coupon.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	public Coupon getCouponById(Long couponId) {
		return couponRepository.findById(couponId)
				.orElseThrow(
						() -> new NoSuchElementException("존재하지 않는 Coupon couponId: " + couponId)
				);
	}

	public CouponDto registerCoupon(CouponRegisterRequest couponRegisterRequest) {
		Coupon savedCoupon = couponRepository.save(
				toCoupon(couponRegisterRequest)
		);

		return toCouponRegisterResponse(savedCoupon);
	}
}
