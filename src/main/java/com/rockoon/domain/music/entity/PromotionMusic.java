package com.rockoon.domain.music.entity;

import com.rockoon.domain.board.entity.Promotion;
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
public class PromotionMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_music_id")
    private Long id;

    private boolean isOpen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    public static PromotionMusic of(Promotion promotion, Music music, boolean isOpen) {
        PromotionMusic build = PromotionMusic.builder()
                .music(music)
                .promotion(promotion)
                .isOpen(isOpen)
                .build();
        build.link(promotion);
        return build;
    }

    private void link(Promotion promotion) {
        promotion.getPromotionMusicList().add(this);
    }
}