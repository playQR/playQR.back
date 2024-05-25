package com.rockoon.domain.member.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.MemberHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryServiceImpl implements MemberQueryService{
    private final MemberRepository memberRepository;
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

    @Override
    public Member getByKakaoEmail(String kakaoEmail) {
        return memberRepository.findByKakaoEmail(kakaoEmail)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    public Member getMemberByUsername(String username) {
        log.info("execute login");
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }
}
