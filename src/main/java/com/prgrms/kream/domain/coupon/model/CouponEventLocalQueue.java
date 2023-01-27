package com.prgrms.kream.domain.coupon.model;

import java.util.ArrayDeque;
import java.util.Queue;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponEventLocalQueue {
	private static final Queue<CouponEventRegisterRequest> couponEventQueue = new ArrayDeque<>();

	public static boolean addQueue(CouponEventRegisterRequest couponEventRegisterRequest) {
		return couponEventQueue.add(couponEventRegisterRequest);
	}

	public static CouponEventRegisterRequest pollQueue() {
		return couponEventQueue.poll();
	}

	public static long size() {
		return couponEventQueue.size();
	}
}
