package com.rockoon.domain.board.service.promotion;

import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.repository.PromotionRepository;
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
}
