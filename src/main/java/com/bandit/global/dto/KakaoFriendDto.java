package com.bandit.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoFriendDto {
    private String id;
    private String uuid;
    private boolean favorite;
    private String profile_nickname;
    private String profile_thumbnail_image;
}
