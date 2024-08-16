package com.bandit.domain.music.dto;

import com.bandit.domain.like.dto.LikeResponse.MusicLikeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicResponse {
    private Long id;
    private String title;
    private String artist;
    @JsonProperty(value = "isOpen")
    private boolean isOpen;
    @Builder.Default
    private MusicLikeDto musicLikeDto = new MusicLikeDto();
}
