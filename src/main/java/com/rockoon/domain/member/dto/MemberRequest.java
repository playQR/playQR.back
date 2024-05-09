package com.rockoon.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class MemberRequest {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberRegisterDto{
        private String kakaoEmail;
        private String profileImg;
        private String nickname;
        private String name;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberModifyDto{
        private String profileImg;
        private String nickname;
        private String name;
    }
}
