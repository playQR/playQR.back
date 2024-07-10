package com.bandit.domain.like.service.like_promotion;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.like.entity.LikePromotion;
import com.bandit.domain.like.repository.LikePromotionRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.LikeHandler;
import com.bandit.presentation.payload.exception.PromotionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LikePromotionCommandServiceImpl implements LikePromotionCommandService{
    private final LikePromotionRepository likePromotionRepository;
    private final PromotionRepository promotionRepository;
    @Override
    public Long likePromotion(Long promotionId, Member member) {
        validateAlreadyExist(promotionId, member);
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        LikePromotion save = likePromotionRepository.save(LikePromotion.of(promotion, member));
        return save.getId();
    }

    @Override
    public void unlikePromotion(Long promotionId, Member member) {
        validateNotFound(promotionId, member);
        likePromotionRepository.deleteByPromotionIdAndMember(promotionId, member);
    }
    private void validateAlreadyExist(Long musicId, Member member) {
        if (likePromotionRepository.existsByPromotionIdAndMember(musicId, member)) {
            throw new LikeHandler(ErrorStatus.LIKE_ALREADY_EXIST);
        }
    }

    private void validateNotFound(Long musicId, Member member) {
        if (!likePromotionRepository.existsByPromotionIdAndMember(musicId, member)) {
            throw new LikeHandler(ErrorStatus.LIKE_NOT_FOUND);
        }
    }
}
