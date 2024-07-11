package com.bandit.domain.member.converter;

import com.bandit.domain.member.dto.MemberResponse;
import com.bandit.domain.member.entity.Member;
import com.bandit.global.util.ImageUtil;
import com.bandit.global.util.NameUtil;

public class MemberConverter {
    public static MemberResponse toResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(NameUtil.maskLastName(member.getName()))
                .profileImg(ImageUtil.appendUri(member))
                .nickname(member.getNickname())
                .build();
    }

    public static MemberResponse toResponseNotPrivate(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .profileImg(ImageUtil.appendUri(member))
                .nickname(member.getNickname())
                .build();
    }
}
