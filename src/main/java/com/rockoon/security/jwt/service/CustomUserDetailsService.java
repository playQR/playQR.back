package com.rockoon.security.jwt.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberQueryService memberQueryService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberByUsername = memberQueryService.getMemberByUsername(username);
        return memberByUsername;
    }
}
