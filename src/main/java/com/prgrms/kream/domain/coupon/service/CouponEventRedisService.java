package com.prgrms.kream.domain.coupon.service;

import org.springframework.stereotype.Service;

import com.prgrms.kream.common.config.CouponProperties;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.model.CouponEventLocalQueue;
import com.prgrms.kream.domain.coupon.repository.CouponEventRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponEventRedisService {
	private final CouponEventRedisRepository couponEventRedisRepository;
	private long throughput = CouponProperties.getThroughput();

	public long registerCouponEventToRedis(CouponEventRegisterRequest couponEventRegisterRequest) {
		return couponEventRedisRepository.register(couponEventRegisterRequest);

	}

	public Long getRedisSize(String key) {
		return couponEventRedisRepository.getSize(key);
	}

	public void registerCouponEventToLocalQueue(String key) {
		tuneThroughput();
		long size = getRedisSize(key);

		if (size > throughput) {
			registerToLocalQueue(key, throughput);
			return;
		}
		if (size <= throughput) {
			registerToLocalQueue(key, size);
			return;
		}
	}

	public void deleteAllCouponEvent(String key) {
		couponEventRedisRepository.deleteAll(key);
	}

	private void registerToLocalQueue(String key, long size) {
		for (int i = 0; i < size; i++) {
			CouponEventLocalQueue.add(
					couponEventRedisRepository.pop(key)
			);
		}
	}

	private Long getRank(CouponEventRegisterRequest couponEventRegisterRequest) {
		return couponEventRedisRepository.rank(couponEventRegisterRequest);
	}

	private void tuneThroughput() {
		long queueSize = CouponEventLocalQueue.size();
		if (queueSize == 0) {
			throughput += 15;
			return;
		}
		if (queueSize > throughput) {
			throughput = 0;
			return;
		}
		if (queueSize > 0) {
			throughput -= queueSize;
			return;
		}
	}

}
