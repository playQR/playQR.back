package com.bandit.domain.music.converter;


import com.bandit.domain.music.dto.MusicResponse;
import com.bandit.domain.music.entity.Music;

public class MusicConverter {

    public static MusicResponse toViewDto(Music music) {
        return MusicResponse.builder()
                .id(music.getId())
                .artist(music.getArtist())
                .title(music.getTitle())
                .isOpen(music.getIsOpen())
                .build();
    }
}
