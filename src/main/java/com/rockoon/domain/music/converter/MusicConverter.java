package com.rockoon.domain.music.converter;

import com.rockoon.domain.music.dto.MusicResponse;
import com.rockoon.domain.music.entity.PromotionMusic;

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
