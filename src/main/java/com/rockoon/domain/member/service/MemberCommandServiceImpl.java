package com.rockoon.domain.member.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.web.dto.member.MemberRequest.MemberModifyDto;
import com.rockoon.web.dto.member.MemberRequest.MemberReigsterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    @Override
    public Long registerMember(MemberReigsterDto memberRequest) {
        Member member = Member.of(memberRequest);
        member.setUsername(String.valueOf(UUID.randomUUID()));      //TODO USERNAME UTIL
        return memberRepository.save(member).getId();
    }

    @Override
    public Long modifyMemberInfo(MemberModifyDto memberRequest) {

        return null;
    }
}
