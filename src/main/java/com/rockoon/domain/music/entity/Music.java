package com.rockoon.domain.music.entity;

import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.auditing.entity.BaseTimeEntity;
import com.rockoon.domain.music.dto.MusicRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Music extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    private String title;
    private String artist;

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
