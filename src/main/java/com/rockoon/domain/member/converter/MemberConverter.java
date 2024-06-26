package com.rockoon.domain.member.converter;

import com.rockoon.domain.member.dto.MemberResponse;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.global.util.ImageUtil;

public class MemberConverter {
    public static MemberResponse toResponse(Member member) {
        return MemberResponse.builder()
                .name(member.getName())
                .profileImg(ImageUtil.appendUri(member))
                .nickname(member.getNickname())
                .build();
    }
}
