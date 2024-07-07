package com.bandit.domain.board.service.promotion;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.PromotionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PromotionQueryServiceImpl implements PromotionQueryService{

    private final PromotionRepository promotionRepository;

    @Override
    public List<Promotion> getAll() {
        return promotionRepository.findAll();
    }

    @Override
    public List<Promotion> getAllByLatest() {
        return promotionRepository.findPromotionsByOrderByCreatedDateAsc();
    }

    @Override
    public Page<Promotion> getPaginationPromotion(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    @Override
    public Page<Promotion> getMyPaginationPromotion(Member member, Pageable pageable) {
        return promotionRepository.findByWriter(member, pageable);
    }

    @Override
    public Page<Promotion> searchPaginationPromotion(String keyword, Pageable pageable) {
        return promotionRepository.searchPromotion(keyword, pageable);
    }

    @Override
    public Promotion getPromotionById(Long promotionId) {
        return promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
    }

}
