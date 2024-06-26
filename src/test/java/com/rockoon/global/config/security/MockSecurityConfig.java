package com.rockoon.global.config.security;

import com.rockoon.security.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.rockoon.security.oauth.handler.OAuth2AuthenticationSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("test")
public class MockSecurityConfig {
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return Mockito.mock(OAuth2AuthenticationSuccessHandler.class);
    }
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return Mockito.mock(OAuth2AuthenticationFailureHandler.class);
    }
}
