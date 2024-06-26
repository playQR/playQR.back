package com.bandit.domain.music.converter;


import com.bandit.domain.music.dto.MusicResponse;
import com.bandit.domain.music.entity.PromotionMusic;

public class MusicConverter {

    public static MusicResponse toViewDto(PromotionMusic promotionMusic) {
        return MusicResponse.builder()
                .id(promotionMusic.getId())
                .artist(promotionMusic.getMusic().getArtist())
                .title(promotionMusic.getMusic().getTitle())
                .isOpen(promotionMusic.isOpen())
                .build();
    }
}
