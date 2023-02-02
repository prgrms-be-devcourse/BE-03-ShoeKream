package com.prgrms.kream.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "coupon")
public class CouponProperties {
	static String key;
	static long throughput;

	public void setKey(String key) {
		CouponProperties.key = key;
	}

	public void setThroughput(long throughput) {
		CouponProperties.throughput = throughput;
	}

	public static String getKey() {
		return key;
	}

	public static Long getThroughput() {
		return throughput;
	}
}
