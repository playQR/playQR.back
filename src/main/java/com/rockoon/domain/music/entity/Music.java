package com.rockoon.domain.music.entity;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.web.dto.music.MusicRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@SuperBuilder
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    private String title;
    private String artist;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    public static Music of(Promotion savePromotion, MusicRequest musicRequest) {
        return Music.builder()
                .artist(musicRequest.getArtist())
                .title(musicRequest.getTitle())
                .promotion(savePromotion)
                .build();
    }
}
