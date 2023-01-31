package com.prgrms.kream.domain.coupon.repository;

import java.util.Objects;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponEventRedisRepository {
	private final RedisTemplate<Long, CouponEventRegisterRequest> couponEventRedisTemplate;

	public void add(CouponEventRegisterRequest couponEventRegisterRequest) {
		couponEventRedisTemplate.opsForZSet()
				.addIfAbsent(
						couponEventRegisterRequest.couponId(),
						couponEventRegisterRequest, System.currentTimeMillis()
				);
	}

	public Long size(long key) {
		return couponEventRedisTemplate.opsForZSet()
				.size(key);
	}

	public Set<CouponEventRegisterRequest> range(long key, long start, long end) {
		return couponEventRedisTemplate.opsForZSet()
				.range(key, start, end);
	}

	public void removeRange(long key, long start, long end) {
		couponEventRedisTemplate.opsForZSet()
				.removeRange(key, start, end);
	}

	public Long rank(CouponEventRegisterRequest couponEventRegisterRequest) {
		return couponEventRedisTemplate.opsForZSet()
				.rank(
						couponEventRegisterRequest.couponId(),
						couponEventRegisterRequest
				);
	}

	public CouponEventRegisterRequest pop(long key) {
		return Objects.requireNonNull(
				couponEventRedisTemplate.opsForZSet()
						.popMin(key)
		).getValue();
	}
}
