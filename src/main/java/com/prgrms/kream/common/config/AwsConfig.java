package com.prgrms.kream.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfig {

	private final String accessKey;

	private final String secretKey;

	private final String region;

	public AwsConfig(
			@Value("${cloud.aws.credentials.access-key}") String accessKey,
			@Value("${cloud.aws.credentials.secret-key}") String secretKey,
			@Value("${cloud.aws.region.static}") String region) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.region = region;
	}

	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return AmazonS3ClientBuilder.standard()
				.withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.build();
	}
}
