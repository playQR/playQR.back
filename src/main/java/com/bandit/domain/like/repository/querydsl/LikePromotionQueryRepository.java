package com.bandit.domain.like.repository.querydsl;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikePromotionQueryRepository {

    Page<Promotion> findPromotionByMember(Member member, Pageable pageable);
}
