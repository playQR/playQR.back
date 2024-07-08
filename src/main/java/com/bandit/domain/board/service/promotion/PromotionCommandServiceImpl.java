package com.bandit.domain.board.service.promotion;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.image.entity.Image;
import com.bandit.domain.image.repository.ImageRepository;
import com.bandit.domain.like.repository.LikeMusicRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.music.entity.Music;
import com.bandit.domain.music.entity.PromotionMusic;
import com.bandit.domain.music.repository.MusicRepository;
import com.bandit.domain.music.repository.PromotionMusicRepository;
import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.repository.TicketRepository;
import com.bandit.global.service.AwsS3Service;
import com.bandit.global.util.ListUtil;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.PromotionHandler;
import com.bandit.presentation.payload.exception.TicketHandler;
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
    private final ImageRepository imageRepository;
    private final MusicRepository musicRepository;
    private final PromotionMusicRepository promotionMusicRepository;
    private final LikeMusicRepository likeMusicRepository;
    private final TicketRepository ticketRepository;
    private final AwsS3Service awsS3Service;

    @Override
    public Long createPromotion(Member member, PromotionRequest request) {
        Promotion savePromotion = promotionRepository.save(Promotion.of(member, request));
        saveImageListInPromotion(request, savePromotion);
        saveMusicListInPromotion(request, savePromotion);
        saveTicketWithPromotion(request, savePromotion);
        return savePromotion.getId();
    }



    @Override
    public Long modifyPromotion(Member member, Long promotionId, PromotionRequest request) {
        Promotion updatePromotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        validateWriter(member, updatePromotion);

        updatePromotion.getBoardImageList().stream()
                .map(Image::getImageUrl)
                .filter(image -> !request.getImageList().contains(image))
                .forEach(awsS3Service::deleteFile);
        imageRepository.deleteAllByBoardId(promotionId);
        promotionMusicRepository.deleteAllByPromotionId(promotionId);
        Ticket ticket = ticketRepository.findByPromotionId(promotionId)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.TICKET_NOT_FOUND));

        updatePromotion.update(request);
        ticket.update(request.getShowDate());
        saveImageListInPromotion(request, updatePromotion);
        saveMusicListInPromotion(request, updatePromotion);
        return updatePromotion.getId();
    }

    @Override
    public void removePromotion(Member member, Long promotionId) {
        Promotion removePromotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        validateWriter(member, removePromotion);

        removePromotion.getBoardImageList().stream()
                .map(Image::getImageUrl)
                .forEach(awsS3Service::deleteFile);
        imageRepository.deleteAllByBoardId(promotionId);
        promotionMusicRepository.deleteAllByPromotionId(promotionId);
        ticketRepository.deleteByPromotionId(promotionId);
        promotionRepository.delete(removePromotion);
    }

    private void saveImageListInPromotion(PromotionRequest request, Promotion savePromotion) {
        if (!ListUtil.isNullOrEmpty(request.getImageList())) {
            imageRepository.saveAll(request.getImageList().stream()
                    .map(imageRequest -> Image.of(savePromotion, imageRequest)).collect(Collectors.toList()));
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

    private void saveTicketWithPromotion(PromotionRequest request, Promotion savePromotion) {
        ticketRepository.save(Ticket.of(savePromotion, request.getShowDate()));
    }

    private static void validateWriter(Member member, Promotion promotion) {
        if (!member.equals(promotion.getWriter())) {
            throw new PromotionHandler(ErrorStatus.PROMOTION_ONLY_CAN_BE_TOUCHED_BY_WRITER);
        }
    }
}
