package com.bandit.domain.member.service;

import com.bandit.domain.member.entity.Member;

public interface MemberQueryService {
    Member getByMemberId(Long memberId);

    Member getByNickname(String nickname);

    Member getByKakaoEmail(String kakaoEmail);

    Member getMemberByUsername(String username);
}
