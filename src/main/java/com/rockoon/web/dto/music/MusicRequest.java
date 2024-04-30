package com.rockoon.web.dto.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicRequest {
    private String title;
    private String artist;
    private List<String> youtubeUrlList;
}
