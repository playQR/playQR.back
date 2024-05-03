package com.rockoon.domain.team.entity;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.global.entity.BaseTimeEntity;
import com.rockoon.domain.team.dto.TeamRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Team extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private Member leader;

    public static Team of(Member member, TeamRequest teamRequest) {
        return Team.builder()
                .leader(member)
                .name(teamRequest.getTeamName())
                .build();
    }
}
