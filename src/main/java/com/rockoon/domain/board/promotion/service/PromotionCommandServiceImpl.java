package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.domain.board.promotion.repository.PromotionRepository;
import com.rockoon.domain.image.entity.Image;
import com.rockoon.domain.image.repository.ImageRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.option.entity.Option;
import com.rockoon.domain.option.repository.OptionRepository;
import com.rockoon.global.util.ListUtil;
import com.rockoon.web.dto.promotion.PromotionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PromotionCommandServiceImpl implements PromotionCommandService {
    private final PromotionRepository promotionRepository;
    private final OptionRepository optionRepository;
    private final ImageRepository imageRepository;

    @Override
    public Long createPromotion(Member member, PromotionRequest request) {
        Promotion savePromotion = promotionRepository.save(Promotion.of(member, request));
        if (!ListUtil.isNullOrEmpty(request.getOptionList())) {
            optionRepository.saveAll(request.getOptionList().stream()
                    .map(optionRequest -> Option.of(savePromotion, optionRequest)).collect(Collectors.toList()));
        }
        if (!ListUtil.isNullOrEmpty(request.getImageList())) {
            imageRepository.saveAll(request.getImageList().stream()
                    .map(imageRequest -> Image.of(savePromotion, imageRequest)).collect(Collectors.toList()));
        }
        return savePromotion.getId();
    }

    @Override
    public Long updatePromotion(Member member, Long promotionId, PromotionRequest request) {
        return null;
    }

    @Override
    public void deletePromotion(Member member, Long promotionId) {

    }
}
