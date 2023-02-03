package com.prgrms.kream.domain.coupon.model;

import java.util.ArrayDeque;
import java.util.Queue;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponEventLocalQueue {
	private static Queue<CouponEventRegisterRequest> couponEventQueue = new ArrayDeque<>();

	public static boolean add(CouponEventRegisterRequest couponEventRegisterRequest) {
		return couponEventQueue.add(couponEventRegisterRequest);
	}

	public static CouponEventRegisterRequest poll() {
		return couponEventQueue.poll();
	}

	public static long size() {
		return couponEventQueue.size();
	}

	public static void removeAll() {
		couponEventQueue = new ArrayDeque<>();
	}
}
