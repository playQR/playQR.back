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
import com.rockoon.domain.team.entity.Team;
import com.rockoon.domain.team.repository.TeamRepository;
import com.rockoon.global.util.ListUtil;
import com.rockoon.web.dto.promotion.PromotionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PromotionCommandServiceImpl implements PromotionCommandService {
    private final PromotionRepository promotionRepository;
    private final TeamRepository teamRepository;
    private final OptionRepository optionRepository;
    private final ImageRepository imageRepository;
    private final MusicRepository musicRepository;

    @Override
    public Long savePromotion(Member member, Long teamId, PromotionRequest request) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("not found team"));
        validateWriterIsTeamMember(member, team);
        Promotion savePromotion = promotionRepository.save(Promotion.of(member, team, request));
        saveOptionListInPromotion(request, savePromotion);
        saveImageListInPromotion(request, savePromotion);
        saveMusicListInPromotion(request, savePromotion);
        return savePromotion.getId();
    }

    @Override
    public Long modifyPromotion(Member member, Long promotionId, PromotionRequest request) {
        Promotion updatePromotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("not found promotion"));
        validateWriterIsTeamMember(member,updatePromotion.getTeam());

        optionRepository.deleteAllByBoardId(promotionId);
        imageRepository.deleteAllByBoardId(promotionId);
        musicRepository.deleteAllByPromotionId(promotionId);

        updatePromotion.update(request);
        saveImageListInPromotion(request, updatePromotion);
        saveOptionListInPromotion(request, updatePromotion);
        saveMusicListInPromotion(request, updatePromotion);
        return updatePromotion.getId();
    }

    @Override
    public void removePromotion(Member member, Long promotionId) {
        Promotion removePromotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("not found promotion"));
        validateWriter(member, removePromotion);

        optionRepository.deleteAllByBoardId(promotionId);
        imageRepository.deleteAllByBoardId(promotionId);
        musicRepository.deleteAllByPromotionId(promotionId);
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

    private static void validateWriter(Member member, Promotion promotion) {
        log.info("member = {}", member);
        log.info("writer = {}", promotion.getWriter());
        if (!member.equals(promotion.getWriter())) {
            throw new RuntimeException("cannot touch it");
        }
    }
    private static void validateWriterIsTeamMember(Member member, Team team) {
        if (!team.getTeamMembers().stream().anyMatch(teamMember -> teamMember.getMember().equals(member))) {
            throw new RuntimeException("not belong this team");
        }
    }
}
