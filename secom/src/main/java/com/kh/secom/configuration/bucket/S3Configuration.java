package com.kh.secom.configuration.bucket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Configuration {

	@Value("${cloud.aws.credentials.accesssKey")
	private String accessKey;
	@Value("${cloud.aws.credentials.secretKey")
	private String secretKey;
	@Value("${cloud.aws.region.static")
	private String region;

	@Bean
	public AmazonS3 amazonS3() {

		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.withRegion(region).build();
	}

}
