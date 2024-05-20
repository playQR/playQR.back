package com.rockoon.domain.member.service;

import com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.entity.Role;
import com.rockoon.domain.member.repository.MemberRepository;
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
    public Long registerMember(MemberRegisterDto memberRequest) {
        Member member = Member.of(memberRequest);
        member.setUsername(String.valueOf(UUID.randomUUID()));      //TODO USERNAME UTIL
        member.convertRole(Role.USER);
        return memberRepository.save(member).getId();
    }

    @Override
    public Long modifyMemberInfo(Member member, MemberModifyDto memberRequest) {
        member.modifyInfo(memberRequest);
        return member.getId();
    }
}
