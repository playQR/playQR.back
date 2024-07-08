package com.bandit.domain.member.service;

import com.bandit.domain.member.dto.MemberRequest.MemberModifyDto;
import com.bandit.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.bandit.domain.member.entity.Member;


public interface MemberCommandService {

    Long registerMember(MemberRegisterDto memberRequest);

    Long modifyMemberInfo(Member member , MemberModifyDto memberRequest);

    void removeMember(Member member);

}
