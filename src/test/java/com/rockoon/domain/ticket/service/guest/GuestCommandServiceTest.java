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
    @DisplayName("유저가 프로모션의 게스트를 신청합니다. 신청된 게스트의 상태는 티켓 x, 입장 x상태가 디폴트입니다.")
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

}