package com.bandit.domain.like.service.like_promotion;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.service.promotion.PromotionCommandService;
import com.bandit.domain.board.service.promotion.PromotionQueryService;
import com.bandit.domain.like.repository.LikePromotionRepository;
import com.bandit.domain.member.dto.MemberRequest;
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

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class LikePromotionQueryServiceTest {
    //Service
    @Autowired
    LikePromotionCommandService likePromotionCommandService;
    @Autowired
    LikePromotionQueryService likePromotionQueryService;
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

    Member anotherGuest;
    Promotion promotion;

    @BeforeEach
    void setUp() {
        MemberRequest.MemberRegisterDto guestDto = MemberRequest.MemberRegisterDto.builder()
                .profileImg("guest")
                .nickname("guest")
                .name("guest")
                .kakaoEmail("guest")
                .build();
        MemberRequest.MemberRegisterDto anotherGuestDto = MemberRequest.MemberRegisterDto.builder()
                .profileImg("aguest")
                .nickname("aguest")
                .name("aguest")
                .kakaoEmail("aguest")
                .build();
        MemberRequest.MemberRegisterDto hostDto = MemberRequest.MemberRegisterDto.builder()
                .profileImg("host")
                .nickname("host")
                .name("host")
                .kakaoEmail("host")
                .build();

        Long guestId = memberCommandService.registerMember(guestDto);
        Long anotherGuestId = memberCommandService.registerMember(anotherGuestDto);
        Long hostId = memberCommandService.registerMember(hostDto);

        guest = memberQueryService.getByMemberId(guestId);
        anotherGuest = memberQueryService.getByMemberId(anotherGuestId);
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
    @DisplayName("게스트가 프로모션 좋아요를 눌렀는지 확인합니다.")
    void checkIsLiked() {
        //given
        likePromotionCommandService.likePromotion(promotion.getId(), guest);
        //when
        boolean isLiked = likePromotionQueryService.isLiked(promotion.getId(), guest);
        //then
        assertThat(isLiked).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("프로모션 좋아요의 개수를 확인합니다.")
    void countLikePromotion() {
        //given
        likePromotionCommandService.likePromotion(promotion.getId(), guest);
        likePromotionCommandService.likePromotion(promotion.getId(), anotherGuest);
        //when
        long countLike = likePromotionQueryService.countLike(promotion.getId());
        //then
        assertThat(countLike).isEqualTo(2);
    }

}