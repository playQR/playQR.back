package com.bandit.domain.like.service.like_promotion;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.service.promotion.PromotionCommandService;
import com.bandit.domain.board.service.promotion.PromotionQueryService;
import com.bandit.domain.like.entity.LikePromotion;
import com.bandit.domain.like.repository.LikePromotionRepository;
import com.bandit.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.service.MemberCommandService;
import com.bandit.domain.member.service.MemberQueryService;
import com.bandit.global.config.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class LikePromotionCommandServiceTest {
    //Service
    @Autowired
    LikePromotionCommandService likePromotionCommandService;
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    PromotionQueryService promotionQueryService;
    @Autowired
    MemberCommandService memberCommandService;
    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    //Repository
    @Autowired
    LikePromotionRepository likePromotionRepository;
    //Entity & Dto & Else
    Member host;
    Member guest;
    Promotion promotion;

    @BeforeEach
    void setUp() {
        MemberRegisterDto guestDto = MemberRegisterDto.builder()
                .profileImg("guest")
                .nickname("guest")
                .name("guest")
                .kakaoEmail("guest")
                .build();
        MemberRegisterDto hostDto = MemberRegisterDto.builder()
                .profileImg("host")
                .nickname("host")
                .name("host")
                .kakaoEmail("host")
                .build();

        Long guestId = memberCommandService.registerMember(guestDto);
        Long hostId = memberCommandService.registerMember(hostDto);

        guest = memberQueryService.getByMemberId(guestId);
        host = memberQueryService.getByMemberId(hostId);

        PromotionRequest request = PromotionRequest.builder()
                .team("muje")
                .content("muje")
                .title("title")
                .showDate(LocalDate.of(2024, 8, 2))
                .build();
        Long promotionId = promotionCommandService.createPromotion(host, request);

        promotion = promotionQueryService.getPromotionById(promotionId);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("게스트가 프로모션 좋아요를 누릅니다.")
    void likePromotion() {
        //given
        //setup
        //when
        Long likePromotion = likePromotionCommandService.likePromotion(promotion.getId(), guest);
        //then
        Optional<LikePromotion> optionalLikePromotion = likePromotionRepository.findById(likePromotion);
        assertThat(optionalLikePromotion).isPresent();
    }

    @Test
    @DisplayName("게스트가 프로모션의 좋아요를 취소합니다.")
    void testMethodName() {
        //given
        Long likePromotion = likePromotionCommandService.likePromotion(promotion.getId(), guest);
        //when
        likePromotionCommandService.unlikePromotion(promotion.getId(), guest);
        //then
        List<LikePromotion> all = likePromotionRepository.findAll();
        assertThat(all.size()).isZero();
    }

}