package com.rockoon.domain.team.entity;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.web.dto.team.TeamRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@SuperBuilder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

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
