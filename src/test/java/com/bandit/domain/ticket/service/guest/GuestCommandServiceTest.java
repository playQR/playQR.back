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
import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.domain.ticket.service.ticket.TicketQueryService;
import com.bandit.global.config.test.DatabaseCleanUp;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.GuestHandler;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class GuestCommandServiceTest {
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
    @DisplayName("이미 입장처리된 멤버의 경우 예외를 발생시킵니다.")
    void executeExceptionWhenTryToBookDuplication() {
        //given
        GuestRequest request = GuestRequest.builder()
                .DepositDate(LocalDate.of(2024, 7, 6))
                .reservationCount(2)
                .name("이정한")
                .build();
        Long guestId = guestCommandService.createGuest(promotion.getId(), guest, request);
        //when & then
        assertThatThrownBy(() -> guestCommandService.createGuest(promotion.getId(), guest, request))
                .isInstanceOf(GuestHandler.class)
                .hasMessage(ErrorStatus.GUEST_ALREADY_EXIST.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("티켓의 uuid를 통해 게스트 입장처리를 합니다.")
    void entrance() {
        //given
        GuestRequest request = GuestRequest.builder()
                .DepositDate(LocalDate.of(2024, 7, 6))
                .reservationCount(2)
                .name("이정한")
                .build();
        Long guestId = guestCommandService.createGuest(promotion.getId(), guest, request);
        Ticket ticket = ticketQueryService.findTicketByPromotionId(promotion.getId(), host);
        //when
        log.info("uuid = {}", ticket.getUuid());
        guestCommandService.entrance(ticket.getUuid(), guest);
        //then
        Optional<Guest> guestOptional = guestRepository.findById(guestId);
        assertThat(guestOptional).isPresent()
                .get()
                .extracting("isEntered", "depositDate")
                .containsExactly(true, request.getDepositDate());
    }
    @Test
    @Transactional
    @DisplayName("입장을 두번 이상 시도하려고 하면 예외를 발생시킵니다.")
    void executeExceptionWhenTryToEntranceOverThanTwice() {
        //given
        GuestRequest request = GuestRequest.builder()
                .DepositDate(LocalDate.of(2024, 7, 6))
                .reservationCount(2)
                .name("이정한")
                .build();
        Long guestId = guestCommandService.createGuest(promotion.getId(), guest, request);
        Ticket ticket = ticketQueryService.findTicketByPromotionId(promotion.getId(), host);
        guestCommandService.entrance(ticket.getUuid(), guest);
        //when & then
        assertThatThrownBy(() -> guestCommandService.entrance(ticket.getUuid(), guest))
                .isInstanceOf(GuestHandler.class)
                .hasMessage(ErrorStatus.GUEST_ALREADY_ENTRNACED.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("게스트 내용을 수정합니다. 실제 서비스에서는 해당 내용을 사용하지 않을 예정입니다.")
    void updateGuest() {
        //given
        GuestRequest request = GuestRequest.builder()
                .DepositDate(LocalDate.of(2024, 7, 6))
                .reservationCount(2)
                .name("이정한")
                .build();
        GuestRequest updateRequest = GuestRequest.builder()
                .DepositDate(LocalDate.of(2024, 7, 7))
                .reservationCount(3)
                .name("정정한")
                .build();
        Long guestId = guestCommandService.createGuest(promotion.getId(), guest, request);
        //when
        Long updatedGuestId = guestCommandService.updateGuest(guestId, guest, updateRequest);
        //then
        Optional<Guest> guestOptional = guestRepository.findById(updatedGuestId);
        assertThat(guestOptional).isPresent()
                .get()
                .extracting("depositDate", "reservationCount", "name", "isEntered")
                .containsExactly(updateRequest.getDepositDate(), updateRequest.getReservationCount(),
                        updateRequest.getName(), false);
    }

    @Test
    void deleteGuest() {
        //given
        GuestRequest request = GuestRequest.builder()
                .DepositDate(LocalDate.of(2024, 7, 6))
                .reservationCount(2)
                .name("이정한")
                .build();
        Long guestId = guestCommandService.createGuest(promotion.getId(), guest, request);
        //when
        guestCommandService.deleteGuest(guestId, guest);
        //then
        List<Guest> all = guestRepository.findAll();
        assertThat(all.size()).isZero();
    }
}