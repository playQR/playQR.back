package com.bandit.domain.music.dto;

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
    private boolean isOpen;
}
