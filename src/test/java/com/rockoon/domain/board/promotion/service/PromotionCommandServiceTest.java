package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.domain.board.promotion.repository.PromotionRepository;
import com.rockoon.domain.image.entity.Image;
import com.rockoon.domain.image.repository.ImageRepository;
import com.rockoon.domain.music.repository.MusicRepository;
import com.rockoon.domain.option.entity.Option;
import com.rockoon.domain.option.repository.OptionRepository;
import com.rockoon.global.test.DatabaseCleanUp;
import com.rockoon.web.dto.promotion.PromotionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PromotionCommandServiceTest {
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    MusicRepository musicRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("promotion글을 작성한다. 연관 엔티티 작성 제외")
    void createPromotion() {
        //given
        PromotionRequest request = PromotionRequest.builder()
                .title("promotion test")
                .content("promotion")
                .maxAudience(3)
                .build();

        //when
        Long promotionId = promotionCommandService.createPromotion(request);

        //then
        Promotion promotion = promotionRepository.findById(promotionId).get();
        assertThat(promotion.getMaxAudience()).isEqualTo(request.getMaxAudience());
        assertThat(promotion.getContent()).isEqualTo(request.getContent());
        assertThat(promotion.getTitle()).isEqualTo(request.getTitle());
    }
}