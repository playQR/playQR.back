package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.web.dto.promotion.PromotionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PromotionCommandServiceImpl implements PromotionCommandService {
    @Override
    public Long createPromotion(PromotionRequest request) {
        return null;
    }

    @Override
    public Long updatePromotion(Long promotionId, Member member, PromotionRequest request) {
        return null;
    }

    @Override
    public void deletePromotion(Long promotionId, Member member) {

    }
}
