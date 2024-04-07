package com.rockoon.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id", updatable = false, unique = true, nullable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String kakaoEmail;
    @Column(unique = true, nullable = false)
    private String username;
    private String position;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String profileImg;
    private String name;
    private String nickname;

}
