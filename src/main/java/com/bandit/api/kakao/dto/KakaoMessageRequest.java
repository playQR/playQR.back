package com.bandit.api.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoMessageRequest {
    private String buttonUrl;
    private List<String> receiverUuids;

}
