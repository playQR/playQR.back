package com.bandit.domain.like.service.like_promotion;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.like.repository.LikePromotionRepository;
import com.bandit.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikePromotionQueryServiceImpl implements LikePromotionQueryService{
    private final LikePromotionRepository likePromotionRepository;
    private final PromotionRepository promotionRepository;
    @Override
    public boolean isLiked(Long promotionId, Member member) {
        return likePromotionRepository.existsByPromotionIdAndMember(promotionId, member);
    }

    @Override
    public long countLike(Long promotionId) {
        return likePromotionRepository.countByPromotionId(promotionId);
    }

    @Override
    public Page<Promotion> getMyFavoritePromotionList(Member member, Pageable pageable) {
        return likePromotionRepository.findPromotionByMember(member, pageable);
    }
}
