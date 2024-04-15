package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.domain.board.promotion.repository.PromotionRepository;
import com.rockoon.domain.image.entity.Image;
import com.rockoon.domain.image.repository.ImageRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.music.entity.Music;
import com.rockoon.domain.music.repository.MusicRepository;
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
    private final MusicRepository musicRepository;

    @Override
    public Long createPromotion(Member member, PromotionRequest request) {
        Promotion savePromotion = promotionRepository.save(Promotion.of(member, request));
        saveOptionListInPromotion(request, savePromotion);
        saveImageListInPromotion(request, savePromotion);
        saveMusicListInPromotion(request, savePromotion);
        return savePromotion.getId();
    }

    @Override
    public Long updatePromotion(Member member, Long promotionId, PromotionRequest request) {
        Promotion updatePromotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("not found promotion"));
        updatePromotion.update(request);
        optionRepository.deleteAllByBoardId(promotionId);
        imageRepository.deleteAllByBoardId(promotionId);
        musicRepository.deleteAllByPromotionId(promotionId);
        saveImageListInPromotion(request, updatePromotion);
        saveOptionListInPromotion(request, updatePromotion);
        saveMusicListInPromotion(request, updatePromotion);
        return updatePromotion.getId();
    }

    @Override
    public void deletePromotion(Member member, Long promotionId) {

    }

    private void saveImageListInPromotion(PromotionRequest request, Promotion savePromotion) {
        if (!ListUtil.isNullOrEmpty(request.getImageList())) {
            imageRepository.saveAll(request.getImageList().stream()
                    .map(imageRequest -> Image.of(savePromotion, imageRequest)).collect(Collectors.toList()));
        }
    }

    private void saveOptionListInPromotion(PromotionRequest request, Promotion savePromotion) {
        if (!ListUtil.isNullOrEmpty(request.getOptionList())) {
            optionRepository.saveAll(request.getOptionList().stream()
                    .map(optionRequest -> Option.of(savePromotion, optionRequest)).collect(Collectors.toList()));
        }
    }

    private void saveMusicListInPromotion(PromotionRequest request, Promotion savePromotion) {
        if (!ListUtil.isNullOrEmpty(request.getMusicList())) {
            musicRepository.saveAll(request.getMusicList().stream()
                    .map(musicRequest -> Music.of(savePromotion, musicRequest)).collect(Collectors.toList()));
        }
    }
}
