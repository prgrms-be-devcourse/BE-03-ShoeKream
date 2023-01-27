package com.prgrms.kream.domain.coupon.service;

import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.model.CouponEventLocalQueue;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponEventRedisService {
	private final RedisTemplate<Long, CouponEventRegisterRequest> couponEventRedisTemplate;
	private long throughput = 100;

	public void addRedisQueue(CouponEventRegisterRequest couponEventRegisterRequest) {
		couponEventRedisTemplate.opsForZSet().add(
				couponEventRegisterRequest.couponId(),
				//TODO memberId만 넣을지 고민
				couponEventRegisterRequest,
				System.currentTimeMillis()
		);
	}

	public Long getRedisQueueSize(Long couponId) {
		return couponEventRedisTemplate.opsForZSet()
				.zCard(couponId);
	}

	public void toLocalQueue(Long couponId) {
		tuningThroughput();
		long size = getRedisQueueSize(couponId);

		if (size > throughput) {
			addLocalQueue(couponId, throughput);
			return;
		}
		if (size <= throughput) {
			addLocalQueue(couponId, size);
			return;
		}
	}

	private void addLocalQueue(Long couponId, Long size) {
		for (int i = 0; i < size; i++) {
			CouponEventLocalQueue.addQueue(
					Objects.requireNonNull(
							couponEventRedisTemplate.opsForZSet()
									.popMin(couponId)
							).getValue()
			);
		}
	}

	private void tuningThroughput() {
		System.out.println(throughput + "#########");
		long size = CouponEventLocalQueue.size();
		if (size == 0) {
			throughput += 10;
			return;
		}
		if (size > throughput) {
			throughput = 0;
			return;
		}
		if (size > 0) {
			throughput -= size;
			return;
		}
	}

}
