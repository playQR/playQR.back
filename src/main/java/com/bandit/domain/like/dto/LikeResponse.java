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
        @Builder.Default
        private boolean isLiked = false;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MusicLikeDto{
        private long count;
        @Builder.Default
        private boolean isLiked = false;
    }
}
