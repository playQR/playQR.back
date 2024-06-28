package com.rockoon.domain.ticket.service.guest;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.global.config.test.DatabaseCleanUp;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.GuestHandler;
import com.rockoon.presentation.payload.exception.PromotionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    MemberCommandService memberCommandService;
    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    //Repository
    @Autowired
    GuestRepository guestRepository;

    //Entity & dto & else
    Member host;
    Member guest;
    @BeforeEach
    void setUp() {
        MemberRegisterDto hostDto = MemberRegisterDto.builder()
                .kakaoEmail("hostEmail")
                .name("host")
                .nickname("host")
                .profileImg("host")
                .build();
        MemberRegisterDto guestDto = MemberRegisterDto.builder()
                .kakaoEmail("guestEmail")
                .name("guest")
                .nickname("guest")
                .profileImg("guest")
                .build();
        Long hostId = memberCommandService.registerMember(hostDto);
        Long guestId = memberCommandService.registerMember(guestDto);
        host = memberQueryService.getByMemberId(hostId);
        guest = memberQueryService.getByMemberId(guestId);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("유저가 프로모션의 게스트를 신청합니다. 신청된 게스트의 상태는 티켓 false, 입장 false 상태가 디폴트입니다.")
    void createGuest() {
        //given
        PromotionRequest request = PromotionRequest.builder()
                .title("title")
                .team("team")
                .content("content")
                .maxAudience(3)
                .build();
        Long promotionId = promotionCommandService.createPromotion(host, request);
        //when
        Long guestId = guestCommandService.createGuest(promotionId, guest, "guestName");    //TODO argument name -> GuestRequest(Dto)
        //then
        Optional<Guest> byId = guestRepository.findById(guestId);
        assertThat(byId).isPresent();
        assertThat(byId.get().getEntered()).isFalse();
        assertThat(byId.get().getTicketIssued()).isFalse();
        assertThat(byId.get().getName()).isEqualTo("guestName");
    }
    @Test
    @DisplayName("유저가 프로모션의 게스트 신청 시, 프로모션이 없는 경우 예외를 발생시킵니다.")
    void executeExceptionNotFoundPromotion() {
        //given
        Long proxyId = 1000L;
        //when & then
        assertThatThrownBy(() -> guestCommandService.createGuest(proxyId, host, "guestName"))
                .isInstanceOf(PromotionHandler.class)
                .hasMessage(ErrorStatus.PROMOTION_NOT_FOUND.getMessage());
    }

    @Test
    @Transactional
    @DisplayName("게스트 정보를 수정합니다. 변경 값은 게스트 이름입니다.")
    void updateGuest() {
        //given
        PromotionRequest request = PromotionRequest.builder()
                .title("title")
                .team("team")
                .content("content")
                .maxAudience(3)
                .build();
        String convertedName = "convertedName";
        Long promotionId = promotionCommandService.createPromotion(host, request);
        Long guestId = guestCommandService.createGuest(promotionId, guest, "guestName");
        //when
        guestCommandService.updateGuest(guestId, guest,  convertedName);   //TODO edit argument
        //then
        Guest updatedGuest = guestRepository.findById(guestId).get();
        assertThat(updatedGuest.getName()).isEqualTo(convertedName);
    }
    @Test
    @DisplayName("게스트 정보를 수정할 때, 신청자와 수정 시도 유저의 정보가 다른 경우 예외를 발생시킵니다.")
    void executeExceptionNotValidateCreator() {
        //given
        PromotionRequest request = PromotionRequest.builder()
                .title("title")
                .team("team")
                .content("content")
                .maxAudience(3)
                .build();
        Member notGuest = Member.builder().build();
        Long promotionId = promotionCommandService.createPromotion(host, request);
        Long guestId = guestCommandService.createGuest(promotionId, guest, "guestName");
        //when & then
        assertThatThrownBy(() -> guestCommandService.updateGuest(guestId, notGuest, "exception"))
                .isInstanceOf(GuestHandler.class)
                .hasMessage(ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR.getMessage());
    }

    @Test
    @DisplayName("게스트 정보를 수정할 때, 게스트의 정보가 없을 경우 예외를 발생시킵니다.")
    void executeExceptionNotFoundGuest() {
        //given
        Long proxyId = 1000L;
        //when & then
        assertThatThrownBy(() -> guestCommandService.updateGuest(proxyId, guest, "exception"))
                .isInstanceOf(GuestHandler.class)
                .hasMessage(ErrorStatus.GUEST_NOT_FOUND.getMessage());
    }
}