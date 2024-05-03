package com.rockoon.domain.member.service;

import com.rockoon.web.dto.member.MemberRequest.MemberModifyDto;
import com.rockoon.web.dto.member.MemberRequest.MemberReigsterDto;


public interface MemberCommandService {

    Long registerMember(MemberReigsterDto memberRequest);

    Long modifyMemberInfo(MemberModifyDto memberRequest);

}
