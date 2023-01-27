package com.prgrms.kream.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("spring.redis")
public class RedisProperties {
	private String host;
	private int port;
}
