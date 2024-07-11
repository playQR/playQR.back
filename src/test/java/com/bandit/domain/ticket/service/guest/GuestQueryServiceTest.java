package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.service.promotion.PromotionCommandService;
import com.bandit.domain.board.service.promotion.PromotionQueryService;
import com.bandit.domain.member.dto.MemberRequest;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.repository.MemberRepository;
import com.bandit.domain.member.service.MemberCommandService;
import com.bandit.domain.member.service.MemberQueryService;
import com.bandit.domain.ticket.dto.guest.GuestRequest;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.domain.ticket.service.ticket.TicketQueryService;
import com.bandit.global.config.test.DatabaseCleanUp;
import com.bandit.global.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class GuestQueryServiceTest {
    //Service
    @Autowired
    GuestCommandService guestCommandService;
    @Autowired
    GuestQueryService guestQueryService;
    @Autowired
    TicketQueryService ticketQueryService;
    @Autowired
    MemberCommandService memberCommandService;
    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    PromotionQueryService promotionQueryService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    //Repository
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuestRepository guestRepository;
    //Entity & Dto & else
    Member host;
    Member guest;

    Promotion promotion;
    Promotion promotion2;

    @BeforeEach
    void setUp() {
        MemberRequest.MemberRegisterDto hostDto = MemberRequest.MemberRegisterDto.builder()
                .kakaoEmail("hostEmail")
                .name("host")
                .nickname("hostNickname")
                .profileImg("hostImg")
                .build();
        MemberRequest.MemberRegisterDto guestDto = MemberRequest.MemberRegisterDto.builder()
                .kakaoEmail("guestEmail")
                .name("guest")
                .nickname("guestNickname")
                .profileImg("guestImg")
                .build();
        Long hostId = memberCommandService.registerMember(hostDto);
        Long guestId = memberCommandService.registerMember(guestDto);

        host = memberQueryService.getByMemberId(hostId);
        guest = memberQueryService.getByMemberId(guestId);

        PromotionRequest request = PromotionRequest.builder()
                .showDate(LocalDate.of(2024, 8, 2))
                .title("title")
                .content("content")
                .team("team")
                .maxAudience(100)
                .build();

        PromotionRequest request2 = PromotionRequest.builder()
                .showDate(LocalDate.of(2024, 8, 2))
                .title("title")
                .content("content")
                .team("team")
                .build();
        Long promotionId = promotionCommandService.createPromotion(host, request);
        Long promotionId2 = promotionCommandService.createPromotion(host, request2);
        promotion = promotionQueryService.getPromotionById(promotionId);
        promotion2 = promotionQueryService.getPromotionById(promotionId2);

    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("게스트로서 자신이 예약한 프로모션들을 불러옵니다.")
    void getPromotionsAsGuest() {
        //given
        GuestRequest request1 = GuestRequest.builder()
                .DepositDate(LocalDate.of(2024, 7, 6))
                .reservationCount(2)
                .name("이정한")
                .build();

        GuestRequest request2 = GuestRequest.builder()
                .DepositDate(LocalDate.of(2024, 7, 6))
                .reservationCount(2)
                .name("이정한")
                .build();
        Long guestId1 = guestCommandService.createGuest(promotion.getId(), guest, request1);
        Long guestId2 = guestCommandService.createGuest(promotion2.getId(), guest, request2);
        //when
        PageRequest pageable = PageRequest.of(0, PageUtil.PROMOTION_SIZE);
        Page<Promotion> paginationPromotion = promotionQueryService.getPaginationPromotionAsGuest(guest, pageable);
        //then
        assertThat(paginationPromotion.getContent().size()).isEqualTo(2);
    }


}