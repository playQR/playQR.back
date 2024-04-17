package com.rockoon.domain.member.service;

import com.rockoon.web.dto.member.MemberRequest;

public interface MemberCommandService {
    Long saveMember(MemberRequest memberRequest);
}
