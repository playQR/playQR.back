package com.bandit.security.oauth.dto;

import com.bandit.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class CustomUserDetails implements OAuth2User, UserDetails {

    private Long id;
    private String username;
    private String kakaoEmail;
    private OAuth2AccessToken oAuth2AccessToken;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomUserDetails(Long id,
                             String username,
                             String kakaoEmail,
                             OAuth2AccessToken oAuth2AccessToken,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.kakaoEmail = kakaoEmail;
        this.oAuth2AccessToken = oAuth2AccessToken;
        this.authorities = authorities;
    }

    public static CustomUserDetails create(Member member) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority(member.getRole().getKey()));

        return new CustomUserDetails(
                member.getId(),
                member.getUsername(),
                member.getKakaoEmail(),
                null,
                authorities
        );
    }

    public static CustomUserDetails create(Member member, Map<String, Object> attributes) {
        CustomUserDetails userDetails = CustomUserDetails.create(member);
        userDetails.setAttributes(attributes);
        return userDetails;
    }

    public static CustomUserDetails create(Member member, OAuth2AccessToken oAuth2AccessToken) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority(member.getRole().getKey()));

        return new CustomUserDetails(
                member.getId(),
                member.getUsername(),
                member.getKakaoEmail(),
                oAuth2AccessToken,
                authorities
        );
    }

    //UserDetail override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // OAuth2User Override
    @Override
    public String getName() {
        return String.valueOf(id);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
