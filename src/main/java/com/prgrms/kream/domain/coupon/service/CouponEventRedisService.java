package com.prgrms.kream.domain.coupon.service;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.model.CouponEventLocalQueue;
import com.prgrms.kream.domain.coupon.repository.CouponEventRedisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponEventRedisService {
	private final CouponEventRedisRepository couponEventRedisRepository;
	private long throughput = DEFAULT_THROUGHPUT;
	private static final long DEFAULT_THROUGHPUT = 100L;

	public long addRedisQueue(CouponEventRegisterRequest couponEventRegisterRequest) {
		couponEventRedisRepository.add(couponEventRegisterRequest);
		return getSize(couponEventRegisterRequest.couponId());
	}

	public Long getSize(long couponId) {
		return couponEventRedisRepository.size(couponId);
	}

	public void toQueue(long couponId) {
		tuningThroughput();
		long size = getSize(couponId);

		if (size > throughput) {
			addToQueue(couponId, throughput);
			return;
		}
		if (size <= throughput) {
			addToQueue(couponId, size);
			return;
		}
	}

	//Option 1 Redis -> LocalQueue pop
	private void addToQueue(long couponId, long size) {
		for (int i = 0; i < size; i++) {
			CouponEventLocalQueue.addQueue(
					couponEventRedisRepository.pop(couponId)
			);
		}
	}

	//Option 2 Local Queue 없이 순차적으로 Redis 에서 pop해서서 사용
	// public CouponEventRegisterRequest pop(long couponId) {
	// 	return Objects.requireNonNull(
	// 			couponEventRedisTemplate.opsForZSet()
	// 					.popMin(couponId)
	// 	).getValue();
	// }

	private Long getRank(CouponEventRegisterRequest couponEventRegisterRequest) {
		return couponEventRedisRepository.rank(couponEventRegisterRequest);
	}

	private void tuningThroughput() {
		//TODO 처리량을 보기 위함, 나중에 삭제
		System.out.println(throughput + "#########");
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
