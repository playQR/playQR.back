package com.rockoon.domain.member.service;

import com.rockoon.domain.member.entity.Member;

public interface MemberQueryService {
    Member getByMemberId(Long memberId);

    Member getByNickname(String nickname);
}
