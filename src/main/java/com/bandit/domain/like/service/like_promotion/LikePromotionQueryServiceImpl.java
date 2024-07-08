package com.bandit.domain.like.service.like_promotion;

import com.bandit.domain.like.repository.LikePromotionRepository;
import com.bandit.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikePromotionQueryServiceImpl implements LikePromotionQueryService{
    private final LikePromotionRepository likePromotionRepository;
    @Override
    public boolean isLiked(Long promotionId, Member member) {
        return likePromotionRepository.existsByPromotionIdAndMember(promotionId, member);
    }

    @Override
    public long countLike(Long promotionId) {
        return likePromotionRepository.countByPromotionId(promotionId);
    }
}
