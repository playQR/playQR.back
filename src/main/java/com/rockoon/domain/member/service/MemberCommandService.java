package com.rockoon.domain.member.service;

import com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;


public interface MemberCommandService {

    Long registerMember(MemberRegisterDto memberRequest);

    Long modifyMemberInfo(Member member , MemberModifyDto memberRequest);

}
