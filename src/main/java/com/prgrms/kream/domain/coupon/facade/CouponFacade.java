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
	private boolean soldOut = false;

	@Transactional
	public CouponEventResponse registerCouponEvent(CouponEventRegisterRequest couponEventRegisterRequest) {
		couponService.decreaseCouponAmount(couponEventRegisterRequest.couponId());
		isSoldOut(couponEventRegisterRequest.couponId());
		validateCouponEventRegisterRequest(couponEventRegisterRequest);
		return couponEventService.registerCouponEvent(couponEventRegisterRequest);
	}

	@Transactional
	public long registerCouponEventToRedis(CouponEventRegisterRequest couponEventRegisterRequest) {
		return couponEventRedisService.registerCouponEventToRedis(couponEventRegisterRequest);
	}

	@Scheduled(fixedRate = 1000, initialDelay = 1_000 * 60 * 5)
	public void registerRedisToLocalQueueSchedule() {
		if (soldOut) {
			couponEventRedisService.deleteAllCouponEvent(CouponProperties.getKey());
			return;
		}
		couponEventRedisService.registerCouponEventToLocalQueue(CouponProperties.getKey());
	}

	@Scheduled(fixedDelay = 1000, initialDelay = 1_000 * 60 * 5)
	public void registerCouponEventFromLocalQueue() {
		if (soldOut) {
			CouponEventLocalQueue.removeAll();
		}
		while (CouponEventLocalQueue.size() > 0) {
			registerCouponEvent(
					CouponEventLocalQueue.poll()
			);
		}
	}

	private void validateCouponEventRegisterRequest(CouponEventRegisterRequest couponEventRegisterRequest) {
		if (!couponService.validateCouponId(couponEventRegisterRequest.couponId())) {
			throw new EntityNotFoundException("존재하지 않는 Coupon Id : " + couponEventRegisterRequest.couponId());
		}
		if (JwtUtil.isValidAccess(couponEventRegisterRequest.memberId())) {
			throw new AccessDeniedException("잘못된 접근");
		}
	}

	private void isSoldOut(long couponId) {
		soldOut = couponService.isSoldOut(couponId);
	}

}
