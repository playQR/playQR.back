package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.domain.board.promotion.repository.PromotionRepository;
import com.rockoon.domain.image.entity.Image;
import com.rockoon.domain.image.repository.ImageRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.entity.Role;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.music.repository.MusicRepository;
import com.rockoon.domain.option.entity.Category;
import com.rockoon.domain.option.entity.Option;
import com.rockoon.domain.option.repository.OptionRepository;
import com.rockoon.global.test.DatabaseCleanUp;
import com.rockoon.web.dto.image.ImageRequest;
import com.rockoon.web.dto.option.OptionRequest;
import com.rockoon.web.dto.promotion.PromotionRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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
    MemberRepository memberRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    Member member1;

    @BeforeEach
    void registerMember() {
        member1 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao@naver.com")
                .username("hann")
                .position("guitar")
                .nickname("hann")
                .build();
        memberRepository.save(member1);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("promotion글을 작성한다. 연관 엔티티 작성 제외")
    void createPromotionWithoutRelationEntity() {
        //given
        PromotionRequest request = PromotionRequest.builder()
                .title("promotion test")
                .content("promotion")
                .maxAudience(3)
                .build();

        //when
        Long promotionId = promotionCommandService.createPromotion(member1, request);

        //then
        Promotion promotion = promotionRepository.findById(promotionId).get();
        assertThat(promotion.getMaxAudience()).isEqualTo(request.getMaxAudience());
        assertThat(promotion.getContent()).isEqualTo(request.getContent());
        assertThat(promotion.getTitle()).isEqualTo(request.getTitle());
    }
    @Test
    @DisplayName("promotion글을 작성한다. 연관 엔티티 포함(image, option)")
    void createPromotionWithRelationEntity() {
        //given
        List<OptionRequest> optionList = new ArrayList<>();
        optionList.add(OptionRequest.builder()
                .category(Category.TIME)
                .content("content")
                .build());
        optionList.add(OptionRequest.builder()
                .category(Category.FEE)
                .content("content")
                .build());
        List<ImageRequest> imageList = new ArrayList<>();
        imageList.add(ImageRequest.builder()
                .imageUrl("image.png")
                .build());
        PromotionRequest request = PromotionRequest.builder()
                .title("promotion test")
                .content("promotion")
                .imageList(imageList)
                .optionList(optionList)
                .maxAudience(3)
                .build();


        //when
        Long promotionId = promotionCommandService.createPromotion(member1, request);

        //then
        List<Option> optionsByBoardId = optionRepository.findOptionsByBoardId(promotionId);
        List<Image> imagesByBoardId = imageRepository.findImagesByBoardId(promotionId);
        assertThat(optionsByBoardId).hasSize(2);
        assertThat(imagesByBoardId).hasSize(1);

    }
}