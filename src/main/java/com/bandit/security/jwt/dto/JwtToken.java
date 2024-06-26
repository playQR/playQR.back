package com.bandit.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
    private String grantType;   // JWT에 대한 인증 타입. Bearer 사용. 이후 HTTP 헤더에 prefix로 붙여줌
    private String accessToken;
    private String refreshToken;

}
