package com.prgrms.kream.domain.coupon.repository;

import java.util.Objects;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.prgrms.kream.common.config.CouponProperties;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponEventRedisRepository {
	private final RedisTemplate<String, CouponEventRegisterRequest> couponEventRedisTemplate;

	public void add(CouponEventRegisterRequest couponEventRegisterRequest) {
		couponEventRedisTemplate.opsForZSet()
				.addIfAbsent(
						CouponProperties.getKey(),
						couponEventRegisterRequest, System.currentTimeMillis()
				);
	}

	public Long size(String key) {
		return couponEventRedisTemplate.opsForZSet()
				.size(key);
	}

	public Set<CouponEventRegisterRequest> range(String key, long start, long end) {
		return couponEventRedisTemplate.opsForZSet()
				.range(key, start, end);
	}

	public void removeRange(String key, long start, long end) {
		couponEventRedisTemplate.opsForZSet()
				.removeRange(key, start, end);
	}

	public Long rank(CouponEventRegisterRequest couponEventRegisterRequest) {
		return couponEventRedisTemplate.opsForZSet()
				.rank(
						CouponProperties.getKey(),
						couponEventRegisterRequest
				);
	}

	public CouponEventRegisterRequest pop(String key) {
		return Objects.requireNonNull(
				couponEventRedisTemplate.opsForZSet()
						.popMin(key)
		).getValue();
	}

	public void removeAll(String key) {
		couponEventRedisTemplate.delete(key);
	}
}
