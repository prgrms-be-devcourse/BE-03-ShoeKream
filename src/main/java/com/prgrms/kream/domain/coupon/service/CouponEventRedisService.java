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

	public long addRedisQueue(CouponEventRegisterRequest couponEventRegisterRequest) {
		couponEventRedisRepository.add(couponEventRegisterRequest);
		return getSize(CouponProperties.getKey());
	}

	public Long getSize(String key) {
		return couponEventRedisRepository.size(key);
	}

	public void toQueue(String key) {
		tuneThroughput();
		long size = getSize(key);

		if (size > throughput) {
			addToQueue(key, throughput);
			return;
		}
		if (size <= throughput) {
			addToQueue(key, size);
			return;
		}
	}

	public void removeAll(String key) {
		couponEventRedisRepository.removeAll(key);
	}

	private void addToQueue(String key, long size) {
		for (int i = 0; i < size; i++) {
			CouponEventLocalQueue.addQueue(
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
