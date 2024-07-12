package com.bandit.domain.manager.service;

import com.bandit.domain.member.entity.Member;

public interface ManagerCommandService {

    void createManager(Long promotionId,  Member member);
    void deleteManager(Long promotionId,  Member member);

}
