package com.prgrms.kream.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Setter;

@Setter
@ConfigurationProperties(prefix = "coupon")
public class CouponProperties {
	static String key;
	static long throughput;

	public static String getKey() {
		return key;
	}

	public static Long getThroughput() {
		return throughput;
	}
}
