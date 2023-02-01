package com.prgrms.kream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.prgrms.kream.common.config.CouponProperties;
import com.prgrms.kream.common.config.RedisProperties;

@EnableScheduling
@EnableConfigurationProperties(value = {RedisProperties.class, CouponProperties.class})
@SpringBootApplication(
		exclude = org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class
)
public class ShoeKreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoeKreamApplication.class, args);
	}

}
