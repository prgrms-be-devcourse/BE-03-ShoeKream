package com.prgrms.kream.domain.coupon.service;

import static com.prgrms.kream.common.mapper.CouponMapper.*;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.coupon.dto.CouponControllerResponse;
import com.prgrms.kream.domain.coupon.dto.CouponRegisterRequest;
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

	public CouponControllerResponse couponRegister(CouponRegisterRequest couponRegisterRequest) {
		Coupon savedCoupon = couponRepository.save(
				toCoupon(couponRegisterRequest)
		);

		return toCouponControllerResponse(savedCoupon);
	}
}
