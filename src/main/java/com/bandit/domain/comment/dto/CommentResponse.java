package com.bandit.domain.comment.dto;

import com.bandit.domain.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponse {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentViewDto{
        private Long id;
        private String content;
        private LocalDateTime createdTime;
        private MemberResponse memberResponse;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentListDto{
        private List<CommentViewDto> commentList;
        private int nextPageParam;
        private long totalCount;
    }
}
