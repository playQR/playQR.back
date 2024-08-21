package com.bandit.domain.board.dto.promotion;

import com.bandit.domain.like.dto.LikeResponse.BoardLikeInfo;
import com.bandit.domain.member.dto.MemberResponse;
import com.bandit.domain.music.dto.MusicResponse;
import com.bandit.domain.ticket.dto.guest.GuestResponse.GuestViewDto;
import com.bandit.domain.ticket.dto.ticket.TicketResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
        private int entranceFee;
        private int maxAudience;
        private LocalDate date;
        private String startTime;
        private String endTime;
        private String location;
        private String bankName;
        private String account;
        private String accountHolder;
        private String refundInfo;
        private MemberResponse writer;
        @Builder.Default
        private BoardLikeInfo boardLikeInfo = new BoardLikeInfo();

        private List<MusicResponse> musicList;
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
        private LocalDate date;
        private String location;
        private String startTime;
        private String endTime;
        private int entranceFee;
        private MemberResponse writer;
        @Builder.Default
        private BoardLikeInfo boardLikeInfo = new BoardLikeInfo();
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPromotionSummaryDto{
        private PromotionSummaryDto promotion;
        private TicketResponse ticket;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestPromotionSummaryDto{
        private PromotionSummaryDto promotion;
        private GuestViewDto guest;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionListDto{
        private List<PromotionSummaryDto> promotionList;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPromotionListDto{
        private List<MyPromotionSummaryDto> promotionList;
        private long totalCount;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestPromotionListDto{
        private List<GuestPromotionSummaryDto> promotionList;
        private long totalCount;
    }
}
