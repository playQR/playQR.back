package com.rockoon.domain.board.dto.promotion;

import com.rockoon.domain.member.dto.MemberResponse;
import com.rockoon.domain.music.dto.MusicResponse;
import com.rockoon.domain.showOption.dto.ShowOptionResponse;
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
