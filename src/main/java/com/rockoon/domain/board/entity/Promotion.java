package com.rockoon.domain.board.entity;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.member.entity.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@DiscriminatorValue("type_promotion")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion extends Board {
    private int maxAudience;

    public static Promotion of(Member member, PromotionRequest request) {
        return Promotion.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .maxAudience(request.getMaxAudience())
                .writer(member)
                .build();
    }

    public void update(PromotionRequest updateRequest) {
        this.maxAudience = updateRequest.getMaxAudience();
        this.title = updateRequest.getTitle();
        this.content = updateRequest.getContent();
    }
}
