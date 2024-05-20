package com.rockoon.domain.member.entity;

import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.auditing.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "member")
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String kakaoEmail;

    @Column(unique = true, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String profileImg;

    private String name;

    private String nickname;

    public static Member of(MemberRegisterDto memberRequest) {
        return Member.builder()
                .profileImg(memberRequest.getProfileImg())
                .name(memberRequest.getName())
                .nickname(memberRequest.getNickname())
                .kakaoEmail(memberRequest.getKakaoEmail())
                .build();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void modifyInfo(MemberModifyDto memberRequest) {
        this.name = memberRequest.getName();
        this.nickname = memberRequest.getNickname();
        this.profileImg = memberRequest.getProfileImg();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.getKey()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void convertRole(Role role) {
        this.role = role;
    }
}
