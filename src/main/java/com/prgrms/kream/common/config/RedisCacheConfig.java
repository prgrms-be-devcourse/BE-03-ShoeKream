package com.prgrms.kream.common.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class RedisCacheConfig {

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

		RedisCacheManager.RedisCacheManagerBuilder redisCacheManagerBuilder
				= RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory);

		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

		return redisCacheManagerBuilder.cacheDefaults(redisCacheConfiguration)
				.withInitialCacheConfigurations(getCacheConfiguration(redisCacheConfiguration))
				.build();
	}

	private Map<String, RedisCacheConfiguration> getCacheConfiguration(RedisCacheConfiguration redisCacheConfiguration) {

		return Arrays.stream(CacheType.values())
				.collect(Collectors.toMap(
						cacheType -> cacheType.getCacheName(),
						cacheType -> redisCacheConfiguration.entryTtl(Duration.ofSeconds(cacheType.getExpiredAfterWrite())))
				);
	}
}
