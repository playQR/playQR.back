package com.bandit.domain.like.entity;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "like_promotion")
public class LikePromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_promotion_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public static LikePromotion of(Promotion promotion, Member member) {
        return LikePromotion.builder()
                .promotion(promotion)
                .member(member)
                .build();
    }
}
