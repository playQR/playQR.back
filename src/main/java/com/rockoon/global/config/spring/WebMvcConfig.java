package com.rockoon.global.config.spring;

import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.annotation.auth.CustomAuthenticationPrincipalArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final MemberQueryService memberQueryService;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CustomAuthenticationPrincipalArgumentResolver(memberQueryService));
    }
}
