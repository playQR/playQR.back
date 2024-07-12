package com.bandit.domain.manager.service;

import com.bandit.domain.member.entity.Member;

public interface ManagerCommandService {

    void addManager(Long boardId,  Member member);
    void removeManager(Long boardId,  Member member);
}
