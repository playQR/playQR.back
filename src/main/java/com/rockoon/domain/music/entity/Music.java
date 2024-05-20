package com.rockoon.domain.music.entity;

import com.rockoon.domain.auditing.entity.BaseTimeEntity;
import com.rockoon.domain.music.dto.MusicRequest;
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

    public static Music of(MusicRequest musicRequest) {
        return Music.builder()
                .artist(musicRequest.getArtist())
                .title(musicRequest.getTitle())
                .build();
    }
}
