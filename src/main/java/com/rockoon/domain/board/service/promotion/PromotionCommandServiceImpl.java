package com.rockoon.domain.board.service.promotion;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.repository.PromotionRepository;
import com.rockoon.domain.image.entity.Image;
import com.rockoon.domain.image.repository.ImageRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.music.entity.Music;
import com.rockoon.domain.music.entity.PromotionMusic;
import com.rockoon.domain.music.repository.MusicRepository;
import com.rockoon.domain.music.repository.PromotionMusicRepository;
import com.rockoon.domain.showOption.entity.ShowOption;
import com.rockoon.domain.showOption.repository.showOptionRepository;
import com.rockoon.global.util.ListUtil;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.PromotionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PromotionCommandServiceImpl implements PromotionCommandService {
    private final PromotionRepository promotionRepository;
    private final showOptionRepository showOptionRepository;
    private final ImageRepository imageRepository;
    private final MusicRepository musicRepository;
    private final PromotionMusicRepository promotionMusicRepository;

    @Override
    public Long createPromotion(Member member, PromotionRequest request) {
        Promotion savePromotion = promotionRepository.save(Promotion.of(member, request));
        saveOptionListInPromotion(request, savePromotion);
        saveImageListInPromotion(request, savePromotion);
        saveMusicListInPromotion(request, savePromotion);
        return savePromotion.getId();
    }

    @Override
    public Long modifyPromotion(Member member, Long promotionId, PromotionRequest request) {
        Promotion updatePromotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        validateWriter(member, updatePromotion);

        showOptionRepository.deleteAllByBoardId(promotionId);
        imageRepository.deleteAllByBoardId(promotionId);
        promotionMusicRepository.deleteAllByPromotionId(promotionId);

        updatePromotion.update(request);
        saveImageListInPromotion(request, updatePromotion);
        saveOptionListInPromotion(request, updatePromotion);
        saveMusicListInPromotion(request, updatePromotion);
        return updatePromotion.getId();
    }

    @Override
    public void removePromotion(Member member, Long promotionId) {
        Promotion removePromotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        validateWriter(member, removePromotion);

        showOptionRepository.deleteAllByBoardId(promotionId);
        imageRepository.deleteAllByBoardId(promotionId);
        promotionMusicRepository.deleteAllByPromotionId(promotionId);
        promotionRepository.delete(removePromotion);
    }

    private void saveImageListInPromotion(PromotionRequest request, Promotion savePromotion) {
        if (!ListUtil.isNullOrEmpty(request.getImageList())) {
            imageRepository.saveAll(request.getImageList().stream()
                    .map(imageRequest -> Image.of(savePromotion, imageRequest)).collect(Collectors.toList()));
        }
    }

    private void saveOptionListInPromotion(PromotionRequest request, Promotion savePromotion) {
        if (!ListUtil.isNullOrEmpty(request.getOptionList())) {
            showOptionRepository.saveAll(request.getOptionList().stream()
                    .map(optionRequest -> ShowOption.of(savePromotion, optionRequest)).collect(Collectors.toList()));
        }
    }

    private void saveMusicListInPromotion(PromotionRequest request, Promotion savePromotion) {
        if (!ListUtil.isNullOrEmpty(request.getMusicList())) {
            List<Music> musicList = musicRepository.saveAll(request.getMusicList().stream()
                    .map(musicRequest -> Music.of(musicRequest)).collect(Collectors.toList()));
            musicList.forEach(music -> promotionMusicRepository.save(
                    PromotionMusic.of(savePromotion, music, true))
            );
            //TODO search -> whiteList music get & newly insert music -> save PromotionMusic
            //TODO setting each isOpen value
        }
    }

    private static void validateWriter(Member member, Promotion promotion) {
        if (!member.equals(promotion.getWriter())) {
            throw new PromotionHandler(ErrorStatus.PROMOTION_ONLY_CAN_BE_TOUCHED_BY_WRITER);
        }
    }
}
