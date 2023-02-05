package com.prgrms.kream.common.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {
	PRODUCT("product", ConstantConfig.DEFAULT_TTL_SEC, ConstantConfig.DEFAULT_MAX_SIZE),
	PRODUCTS("products", ConstantConfig.DEFAULT_TTL_SEC, ConstantConfig.DEFAULT_MAX_SIZE),
	POPULAR_FEEDS("topPopular", ConstantConfig.DEFAULT_TTL_SEC, ConstantConfig.DEFAULT_MAX_SIZE);


	private final String cacheName;
	private final int expiredAfterWrite;
	private final int maximumSize;

	static class ConstantConfig {
		static final int DEFAULT_TTL_SEC = 3600;
		static final int DEFAULT_MAX_SIZE = 10000;
	}
}
