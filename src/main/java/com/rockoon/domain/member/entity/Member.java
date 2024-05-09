package com.rockoon.domain.member.entity;

import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.auditing.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
public class Member extends BaseTimeEntity {

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
}
