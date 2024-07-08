package com.bandit.domain.music.entity;

import com.bandit.domain.auditing.entity.BaseTimeEntity;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.music.dto.MusicRequest;
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
@Table(name = "music")
public class Music extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    private String title;
    private String artist;
    private Boolean isOpen;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    public static Music of(MusicRequest musicRequest, Promotion promotion) {
        return Music.builder()
                .artist(musicRequest.getArtist())
                .title(musicRequest.getTitle())
                .isOpen(musicRequest.getIsOpen())
                .promotion(promotion)
                .build();
    }
}
