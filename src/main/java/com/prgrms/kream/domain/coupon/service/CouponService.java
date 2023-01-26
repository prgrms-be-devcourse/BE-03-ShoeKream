package com.prgrms.kream.domain.coupon.service;

import static com.prgrms.kream.common.mapper.CouponMapper.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.coupon.dto.request.CouponRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponResponse;
import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.coupon.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	@Transactional(readOnly = true)
	public CouponResponse getCouponById(Long couponId) {
		return toCouponResponse(getCoupon(couponId));
	}

	private Coupon getCoupon(Long couponId) {
		return couponRepository.findById(couponId)
				.orElseThrow(
						() -> new EntityNotFoundException("존재하지 않는 Coupon couponId: " + couponId)
				);
	}

	@Transactional
	public void decreaseCouponAmount(Long couponId) {
		getCoupon(couponId).decreaseAmount();
	}

	@Transactional
	public CouponResponse registerCoupon(CouponRegisterRequest couponRegisterRequest) {
		Coupon savedCoupon = couponRepository.save(
				toCoupon(couponRegisterRequest)
		);

		return toCouponResponse(savedCoupon);
	}
}
