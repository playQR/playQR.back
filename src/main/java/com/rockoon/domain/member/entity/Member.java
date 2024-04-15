package com.rockoon.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
