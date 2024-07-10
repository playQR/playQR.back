package com.bandit.domain.manager.service;

import com.bandit.domain.member.entity.Member;

public interface ManagerCommandService {

    void createManager(Long promotionId,  Member member);
    void deleteManager(Long promotionId,  Member member);


    void completeEntrance(Long guestId, Member member);
    void cancelReservation(Long guestId, Member member);
    void confirmReservation(Long guestId, Member member);
}
