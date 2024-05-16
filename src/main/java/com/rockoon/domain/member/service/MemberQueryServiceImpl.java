package com.rockoon.domain.member.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.team.repository.TeamRepository;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.MemberHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryServiceImpl implements MemberQueryService{
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    @Override
    public Member getByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    public Member getByNickname(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }
}
