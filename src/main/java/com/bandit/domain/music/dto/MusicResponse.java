package com.bandit.domain.music.dto;

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
    private long count;
    @JsonProperty(value = "isOpen")
    private boolean isOpen;
}
