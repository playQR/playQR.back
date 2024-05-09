package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionQueryService {
    List<Promotion> getAll();

    List<Promotion> getAllByLatest();

    Page<Promotion> getPaginationPromotion(Pageable pageable);

}
