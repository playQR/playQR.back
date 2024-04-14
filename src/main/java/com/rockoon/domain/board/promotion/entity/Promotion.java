package com.rockoon.domain.board.promotion.entity;

import com.rockoon.domain.board.entity.Board;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.web.dto.promotion.PromotionRequest;
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
                .member(member)
                .build();
    }
}
