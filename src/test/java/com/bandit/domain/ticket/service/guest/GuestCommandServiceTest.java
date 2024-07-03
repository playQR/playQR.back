package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.service.promotion.PromotionCommandService;
import com.bandit.domain.board.service.promotion.PromotionQueryService;
import com.bandit.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.repository.MemberRepository;
import com.bandit.domain.member.service.MemberCommandService;
import com.bandit.domain.member.service.MemberQueryService;
import com.bandit.domain.ticket.dto.guest.GuestRequest;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.repository.GuestRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class GuestCommandServiceTest {
    //Service
    @Autowired
    GuestCommandService guestCommandService;
    @Autowired
    GuestQueryService guestQueryService;
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

    @BeforeEach
    void setUp() {
        MemberRegisterDto hostDto = MemberRegisterDto.builder()
                .kakaoEmail("hostEmail")
                .name("host")
                .nickname("hostNickname")
                .profileImg("hostImg")
                .build();
        MemberRegisterDto guestDto = MemberRegisterDto.builder()
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
    @DisplayName("promotion id를 통해 게스트를 생성합니다.")
    void createGuest() {
        //given
        GuestRequest request = GuestRequest.builder()
                .DepositDate(LocalDate.of(2024, 7, 6))
                .reservationCount(2)
                .name("이정한")
                .build();
        //when
        Long guestId = guestCommandService.createGuest(promotion.getId(), guest, request);
        //then
        Optional<Guest> guestOptional = guestRepository.findById(guestId);
        assertThat(guestOptional).isPresent()
                .get()
                .extracting("depositDate", "reservationCount", "name", "isEntered")
                .containsExactly(request.getDepositDate(), request.getReservationCount(),
                        request.getName(), false);
    }

    @Test
    void entrance() {
    }

    @Test
    void updateGuest() {
    }

    @Test
    void deleteGuest() {
    }
}