package com.bandit.domain.manager.service;

import com.bandit.domain.member.entity.Member;

import java.util.List;

public interface ManagerQueryService {

    List<Member> getManagers(Long promotionId);
}
