package com.rockoon.domain.member.service;

import com.rockoon.domain.member.entity.Member;

import java.util.List;

public interface MemberQueryService {
    Member getByMemberId(Long memberId);

    Member getByNickname(String nickname);

    List<Member> getMembersByTeamId(Long teamId);
}
