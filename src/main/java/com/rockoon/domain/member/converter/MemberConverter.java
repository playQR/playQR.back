package com.rockoon.domain.member.converter;

import com.rockoon.domain.member.dto.MemberResponse;
import com.rockoon.domain.member.entity.Member;

public class MemberConverter {
    public static MemberResponse toResponse(Member member) {
        return MemberResponse.builder()
                .name(member.getName())
                .profileImg(member.getProfileImg())
                .nickname(member.getNickname())
                .build();
    }
}
