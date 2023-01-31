package com.prgrms.kream.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
	private final RedisProperties redisProperties;
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<Long, CouponEventRegisterRequest> couponEventRedisTemplate() {
		RedisTemplate<Long, CouponEventRegisterRequest> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(CouponEventRegisterRequest.class));
		return redisTemplate;
	}

}
