package com.rockoon.global.config.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("test")
public class MockAwsS3Config extends AmazonS3Config{
    @Bean
    @Override
    public AmazonS3Client amazonS3Client() {
        log.info("test");
        return Mockito.mock(AmazonS3Client.class);
    }
}
