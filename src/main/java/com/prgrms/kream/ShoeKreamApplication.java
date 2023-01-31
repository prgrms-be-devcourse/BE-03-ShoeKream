package com.prgrms.kream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(
		exclude = org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class
)
public class ShoeKreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoeKreamApplication.class, args);
	}

}
