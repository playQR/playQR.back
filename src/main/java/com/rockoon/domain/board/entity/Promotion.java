package com.rockoon.domain.board.entity;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.music.entity.PromotionMusic;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@DiscriminatorValue("type_promotion")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion extends Board {
    private int maxAudience;
    private String team;

    @Builder.Default
    @OneToMany(mappedBy = "promotion", orphanRemoval = true)
    private List<PromotionMusic> promotionMusicList = new ArrayList<>();

    public static Promotion of(Member member, PromotionRequest request) {
        return Promotion.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .maxAudience(request.getMaxAudience())
                .team(request.getTeam())
                .writer(member)
                .build();
    }

    public void update(PromotionRequest updateRequest) {
        this.maxAudience = updateRequest.getMaxAudience();
        this.team = updateRequest.getTeam();
        this.title = updateRequest.getTitle();
        this.content = updateRequest.getContent();
    }
}
