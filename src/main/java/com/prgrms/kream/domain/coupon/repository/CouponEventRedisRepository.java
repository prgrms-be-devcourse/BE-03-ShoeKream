package com.prgrms.kream.domain.coupon.repository;

import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.stereotype.Component;

import com.prgrms.kream.common.config.CouponProperties;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponEventRedisRepository {
	private final RedissonClient redissonClient;

	public long register(CouponEventRegisterRequest couponEventRegisterRequest) {
		return getScoredSortedSet(CouponProperties.getKey())
				.addAndGetRank(System.currentTimeMillis(), couponEventRegisterRequest);
	}

	public long getSize(String key) {
		return getScoredSortedSet(key).size();
	}

	public long rank(CouponEventRegisterRequest couponEventRegisterRequest) {
		return getScoredSortedSet(CouponProperties.getKey()).rank(couponEventRegisterRequest);
	}

	public CouponEventRegisterRequest pop(String key) {
		return getScoredSortedSet(key).pollFirst();
	}

	public void deleteAll(String key) {
		redissonClient.getScoredSortedSet(key).delete();
	}

	private RScoredSortedSet<CouponEventRegisterRequest> getScoredSortedSet(String key) {
		return redissonClient.getScoredSortedSet(key, JsonJacksonCodec.INSTANCE);
	}
}
