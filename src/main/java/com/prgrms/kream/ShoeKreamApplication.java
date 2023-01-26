package com.prgrms.kream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		exclude = org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class
)
public class ShoeKreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoeKreamApplication.class, args);
	}

}
