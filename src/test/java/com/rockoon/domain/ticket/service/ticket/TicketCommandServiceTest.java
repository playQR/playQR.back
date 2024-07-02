package com.rockoon.domain.ticket.service.ticket;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.entity.Ticket;
import com.rockoon.domain.ticket.repository.TicketRepository;
import com.rockoon.domain.ticket.service.guest.GuestCommandService;
import com.rockoon.domain.ticket.service.guest.GuestQueryService;
import com.rockoon.global.config.test.DatabaseCleanUp;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.TicketHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class TicketCommandServiceTest {
    //Service
    @Autowired
    TicketCommandService ticketCommandService;
    @Autowired
    GuestCommandService guestCommandService;
    @Autowired
    GuestQueryService guestQueryService;
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    MemberCommandService memberCommandService;
    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    //Repository
    @Autowired
    TicketRepository ticketRepository;
    //Entity & dto & else
    Member host;
    Member guest;
    Guest applyGuest;

    @BeforeEach
    void setUp() {
        MemberRegisterDto hostDto = MemberRegisterDto.builder()
                .name("host")
                .kakaoEmail("hostEmail")
                .profileImg("hostProfile")
                .nickname("hostNickname")
                .build();
        MemberRegisterDto guestDto = MemberRegisterDto.builder()
                .name("guest")
                .kakaoEmail("guestEmail")
                .profileImg("guestProfile")
                .nickname("guestNickname")
                .build();
        Long hostId = memberCommandService.registerMember(hostDto);
        Long guestId = memberCommandService.registerMember(guestDto);

        host = memberQueryService.getByMemberId(hostId);
        guest = memberQueryService.getByMemberId(guestId);

        PromotionRequest request = PromotionRequest.builder()
                .title("title")
                .team("team")
                .content("content")
                .maxAudience(3)
                .build();
        Long promotionId = promotionCommandService.createPromotion(host, request);

        Long applyGuestId = guestCommandService.createGuest(promotionId, guest, "guest");
        applyGuest = guestQueryService.findGuestById(applyGuestId);

    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("호스트가 게스트에게 티켓을 발급합니다. 이때 게스트의 isTicketIssued 값이 true로 변경됩니다.")
    void issueTicket() {
        //given
        //when
        //TODO logic edit (validate guest.getPromotion().getWriter().equals(hostMember)) -> 나중에 여기에 매니저 추가
        Long ticketId = ticketCommandService.createTicket(applyGuest.getId(), host, null);  // dueDate 추가
        //then
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        assertThat(optionalTicket).isPresent();
        assertThat(applyGuest.getTicketIssued()).isTrue();
        assertThat(applyGuest.getEntered()).isFalse();
    }

    @Test
    @DisplayName("호스트가 게스트에게 티켓을 발급할 때, 게스트가 존재하지 않는다면 예외를 발생시킵니다.")
    void executeExceptionNotFoundGuest() {
        //given
        Long proxyGuestId = 1000L;
        //when
        assertThatThrownBy(() -> ticketCommandService.createTicket(proxyGuestId, host, null))
                .isInstanceOf(TicketHandler.class)
                .hasMessage(ErrorStatus.GUEST_NOT_FOUND.getMessage());
        //then
    }

    @Test
    @Transactional
    @DisplayName("QR로부터 받아온 uuid를 통해 티켓을 가져오고, 해당 게스트의 isEntered 값이 true로 변경됩니다.")
    void checkTicket() {
        //given
        Long ticketId = ticketCommandService.createTicket(applyGuest.getId(), host, null);
        Ticket ticket = ticketRepository.findById(ticketId).get();
        String uuid = ticket.getUuid();
        //when
        ticketCommandService.enterByUUID(uuid);    //TODO make method
        //then
        Guest convertedGuest = guestQueryService.findGuestById(ticket.getGuest().getId());
        assertThat(convertedGuest.getEntered()).isTrue();
        assertThat(convertedGuest.getTicketIssued()).isTrue();
    }

    @Test
    @DisplayName("QR로부터 입장처리를 할 때, uuid가 존재하지않을 경우 예외를 발생시킵니다.")
    void executeExceptionNotFoundTicketByUUID() {
        //given
        String uuid = "uuid";
        //when
        assertThatThrownBy(() -> ticketCommandService.enterByUUID(uuid))
                .isInstanceOf(TicketHandler.class)
                .hasMessage(ErrorStatus.TICKET_NOT_FOUND.getMessage());
        //then
    }

    @Test
    @Transactional
    @DisplayName("호스트가 발급된 티켓의 유효성을 삭제합니다.")
    void deleteTicket() {
        //given
        Long ticketId = ticketCommandService.createTicket(applyGuest.getId(), host, null);
        //when
        //TODO need to validate commander is host
        ticketCommandService.deleteTicket(ticketId, host);
        //then
        List<Ticket> all = ticketRepository.findAll();
        assertThat(all.size()).isZero();
        assertThat(applyGuest.getTicketIssued()).isFalse();
    }

    @Test
    @DisplayName("티켓의 유효성을 삭제할 때, 티켓이 존재하지 않는 경우 예외를 발생시킵니다.")
    void executeExceptionNotFoundTicket() {
        //given
        Long proxyTicketId = 10000L;
        //when & then
        assertThatThrownBy(() -> ticketCommandService.deleteTicket(proxyTicketId, host))
                .isInstanceOf(TicketHandler.class)
                .hasMessage(ErrorStatus.TICKET_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("티켓의 유효성을 삭제할 때, 삭제하는 이가 호스트가 아닐 경우 예외를 발생시킵니다.")
    void executeExceptionValidateHost() {
        //when & then
        assertThatThrownBy(() -> ticketCommandService.deleteTicket(applyGuest.getId(), guest))
                .isInstanceOf(TicketHandler.class)
                .hasMessage(ErrorStatus.TICKET_CAN_ONLY_BE_TOUCHED_BY_HOST_AND_MANAGERS.getMessage());
    }

}