package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.domain.member.entity.Member;

import java.util.List;

public interface PromotionQueryService {
    List<Promotion> getAll();

    List<Promotion> getAllByLatest();

    List<Promotion> getByTeam(Long teamId);

    List<Promotion> getByMemberBelongToTeam(Member member, Long teamId);

}
