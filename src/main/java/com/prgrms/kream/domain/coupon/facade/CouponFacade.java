package com.prgrms.kream.domain.coupon.facade;

import javax.persistence.EntityNotFoundException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.common.config.CouponProperties;
import com.prgrms.kream.common.jwt.JwtUtil;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.model.CouponEventLocalQueue;
import com.prgrms.kream.domain.coupon.service.CouponEventRedisService;
import com.prgrms.kream.domain.coupon.service.CouponEventService;
import com.prgrms.kream.domain.coupon.service.CouponService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponFacade {
	private final CouponService couponService;
	private final CouponEventService couponEventService;
	private final CouponEventRedisService couponEventRedisService;
	private boolean soldOutFlag = false;

	@Transactional
	public CouponEventResponse registerCouponEvent(CouponEventRegisterRequest couponEventRegisterRequest) {
		couponService.decreaseCouponAmount(couponEventRegisterRequest.couponId());
		isSoldOut(couponEventRegisterRequest.couponId());
		validCouponEventRegisterRequest(couponEventRegisterRequest);
		return couponEventService.registerCouponEvent(couponEventRegisterRequest);
	}

	@Transactional
	public long applyCountEvent(CouponEventRegisterRequest couponEventRegisterRequest) {
		return couponEventRedisService.addRedisQueue(couponEventRegisterRequest);
	}

	@Scheduled(fixedRate = 1000, initialDelay = 1_000 * 60 * 5)
	public void couponEventSchedule() {
		if (soldOutFlag) {
			couponEventRedisService.removeAll(CouponProperties.getKey());
			return;
		}
		couponEventRedisService.toQueue(CouponProperties.getKey());
	}

	@Scheduled(fixedDelay = 1000, initialDelay = 1_000 * 60 * 5)
	public void couponEventMethod() {
		if (soldOutFlag) {
			CouponEventLocalQueue.removeAll();
		}
		while (CouponEventLocalQueue.size() > 0) {
			registerCouponEvent(
					CouponEventLocalQueue.pollQueue()
			);
		}
	}

	private void validCouponEventRegisterRequest(CouponEventRegisterRequest couponEventRegisterRequest) {
		if (!couponService.validCouponId(couponEventRegisterRequest.couponId())) {
			throw new EntityNotFoundException("존재하지 않는 Coupon Id : " + couponEventRegisterRequest.couponId());
		}
		if (JwtUtil.isValidAccess(couponEventRegisterRequest.memberId())) {
			throw new AccessDeniedException("잘못된 접근");
		}
	}

	private void isSoldOut(long couponId) {
		soldOutFlag = couponService.isSoldOut(couponId);
	}

}
