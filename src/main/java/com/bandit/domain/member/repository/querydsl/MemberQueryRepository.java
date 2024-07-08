package com.bandit.domain.member.repository.querydsl;

import com.bandit.domain.member.entity.Member;

public interface MemberQueryRepository {

    void deleteWithRelations(Member member);
}
