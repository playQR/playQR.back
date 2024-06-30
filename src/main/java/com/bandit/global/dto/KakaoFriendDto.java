package com.bandit.global.dto;

import lombok.Data;

@Data
public class KakaoFriendDto {
    private String id;
    private String uuid;
    private boolean favorite;
    private String profile_nickname;
    private String profile_thumbnail_image;
}
