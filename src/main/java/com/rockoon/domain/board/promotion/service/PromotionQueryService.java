package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionQueryService {
    List<Promotion> getAll();

    List<Promotion> getAllByLatest();

    Page<Promotion> getPaginationPromotion(Pageable pageable);

    List<Promotion> getByTeam(Long teamId);

    List<Promotion> getByMemberBelongToTeam(Member member, Long teamId);

}
