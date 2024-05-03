package com.rockoon.domain.member.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;
import com.rockoon.domain.member.dto.MemberRequest.MemberReigsterDto;


public interface MemberCommandService {

    Long registerMember(MemberReigsterDto memberRequest);

    Long modifyMemberInfo(Member member , MemberModifyDto memberRequest);

}
