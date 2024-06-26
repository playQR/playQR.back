package com.bandit.domain.board.dto.promotion;

import com.bandit.domain.member.dto.MemberResponse;
import com.bandit.domain.music.dto.MusicResponse;
import com.bandit.domain.showOption.dto.ShowOptionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class PromotionResponse {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionDetailDto{
        private Long promotionId;
        private String title;
        private String content;
        private String team;
        private int maxAudience;
        private MemberResponse writer;

        private List<MusicResponse> musicList;
        private List<ShowOptionResponse> optionList;
        private List<String> imageList;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionSummaryDto{
        private Long promotionId;
        private String title;
        private String team;
        private String thumbnail;
        private MemberResponse writer;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionListDto{
        private List<PromotionSummaryDto> promotionList;
    }
}
