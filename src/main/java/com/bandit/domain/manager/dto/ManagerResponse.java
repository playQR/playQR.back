package com.bandit.domain.manager.dto;

import com.bandit.domain.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ManagerResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManagerViewDto {
        private Long managerId;
        private MemberResponse member;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManagerListDto {
        private List<ManagerViewDto> managerList;
    }
}
