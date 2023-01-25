package com.prgrms.kream.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
	private String accessToken;
	private String issuer;
	private String clientSecret;
	private int expirySeconds;

	@Override
	public String toString() {
		return "JwtConfig{" +
				"header='" + accessToken + '\'' +
				", issuer='" + issuer + '\'' +
				", clientSecret='" + clientSecret + '\'' +
				", expirySeconds=" + expirySeconds +
				'}';
	}
}
