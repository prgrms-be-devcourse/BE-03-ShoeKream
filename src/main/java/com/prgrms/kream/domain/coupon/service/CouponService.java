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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	@Transactional
	public CouponResponse getCouponById(long couponId) {
		return toCouponResponse(getCoupon(couponId));
	}

	public boolean validCouponId(long couponId) {
		return couponRepository.existsById(couponId);
	}

	@Transactional
	public void decreaseCouponAmount(long couponId) {
		Coupon coupon = getCoupon(couponId);
		coupon.decreaseAmount();
	}

	@Transactional
	public CouponResponse registerCoupon(CouponRegisterRequest couponRegisterRequest) {
		Coupon savedCoupon = couponRepository.save(
				toCoupon(couponRegisterRequest)
		);

		return toCouponResponse(savedCoupon);
	}

	public boolean checkCouponAmount(long couponId) {
		return getCoupon(couponId).isSoldOut();
	}

	private Coupon getCoupon(long couponId) {
		return couponRepository.findById(couponId)
				.orElseThrow(
						() -> new EntityNotFoundException("존재하지 않는 Coupon couponId: " + couponId)
				);
	}
}
