package com.bandit.domain.board.repository.querydsl;

import com.bandit.domain.board.dto.promotion.PromotionResponse.MyPromotionListDto;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionQueryRepository {

    Page<Promotion> searchPromotion(String keyword, Pageable pageable);

    MyPromotionListDto getMyPromotionWithTicket(Member member, Pageable pageable);
}
