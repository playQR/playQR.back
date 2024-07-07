package com.bandit.domain.member.converter;

import com.bandit.domain.member.dto.MemberResponse;
import com.bandit.domain.member.entity.Member;
import com.bandit.global.util.ImageUtil;

public class MemberConverter {
    public static MemberResponse toResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .profileImg(ImageUtil.appendUri(member))
                .nickname(member.getNickname())
                .build();
    }
}
