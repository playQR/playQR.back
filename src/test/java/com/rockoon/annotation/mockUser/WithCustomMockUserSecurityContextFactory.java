package com.rockoon.annotation.mockUser;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.entity.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<MockAuthUser> {
    @Override
    public SecurityContext createSecurityContext(MockAuthUser annotation) {
        String username = annotation.username();
        String role = annotation.role();

        Member member = Member.builder()
                .kakaoEmail("test@naver.com")
                .nickname("test nickname")
                .username(username)
                .name("test name")
                .role(Role.valueOf(role))
                .build();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(member, "password", List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
