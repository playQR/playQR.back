package com.bandit.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LikeResponse {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardLikeDto{
        private long count;
        private boolean isLiked;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicLikeDto{
        private long count;
        private boolean isLiked;
    }
}
