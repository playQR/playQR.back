package com.bandit.domain.manager;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.board.service.promotion.PromotionCommandService;
import com.bandit.domain.board.service.promotion.PromotionQueryService;
import com.bandit.domain.manager.entity.Manager;
import com.bandit.domain.manager.repository.ManagerRepository;
import com.bandit.domain.manager.service.ManagerCommandService;
import com.bandit.domain.manager.service.ManagerQueryService;
import com.bandit.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.repository.MemberRepository;
import com.bandit.domain.member.service.MemberCommandService;
import com.bandit.domain.member.service.MemberQueryService;
import com.bandit.domain.ticket.dto.guest.GuestRequest;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.domain.ticket.service.guest.GuestCommandService;
import com.bandit.domain.ticket.service.guest.GuestQueryService;
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
class ManagerCommandServiceTest {

    @Autowired
    ManagerCommandService managerCommandService;
    @Autowired
    ManagerQueryService managerQueryService;
    @Autowired
    MemberCommandService memberCommandService;
    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    PromotionQueryService promotionQueryService;
    @Autowired
    GuestCommandService guestCommandService;
    @Autowired
    GuestQueryService guestQueryService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuestRepository guestRepository;

    Member host;
    Member managerMember;
    Promotion promotion;
    Guest guest;


    @BeforeEach
    void setUp() {
        MemberRegisterDto hostDto = MemberRegisterDto.builder()
                .kakaoEmail("hostEmail")
                .name("host")
                .nickname("hostNickname")
                .profileImg("hostImg")
                .build();
        MemberRegisterDto managerDto = MemberRegisterDto.builder()
                .kakaoEmail("managerEmail")
                .name("manager")
                .nickname("managerNickname")
                .profileImg("managerImg")
                .build();
        Long hostId = memberCommandService.registerMember(hostDto);
        Long managerId = memberCommandService.registerMember(managerDto);

        PromotionRequest request = PromotionRequest.builder()
                .showDate(LocalDate.of(2024, 8, 2))
                .title("title")
                .content("content")
                .team("team")
                .build();
        Long promotionId = promotionCommandService.createPromotion(host, request);
        promotion = promotionQueryService.getPromotionById(promotionId);

        guest = createTestGuest(promotion, managerMember);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    private Guest createTestGuest(Promotion promotion, Member member) {
        GuestRequest guestRequest = GuestRequest.builder()
                .name("Test Guest")
                .reservationCount(1)
                .depositDate(LocalDate.now())
                .build();
        Guest guest = Guest.of(promotion, member, guestRequest);
        guestRepository.save(guest);
        return guest;
    }

    @Test
    @Transactional
    @DisplayName("프로모션 ID와 멤버 정보를 통해 매니저를 생성합니다.")
    void createManager() {
        // given
        // when
        managerCommandService.createManager(promotion.getId(), managerMember);

        // then
        Optional<Manager> managerOptional = managerRepository.findByPromotionAndMember(promotion, managerMember);
        assertThat(managerOptional).isPresent();
    }

    @Test
    @Transactional
    @DisplayName("프로모션 ID와 멤버 정보를 통해 매니저를 삭제합니다.")
    void deleteManager() {
        // given
        managerCommandService.createManager(promotion.getId(), managerMember);

        // when
        managerCommandService.deleteManager(promotion.getId(), managerMember);

        // then
        Optional<Manager> managerOptional = managerRepository.findByPromotionAndMember(promotion, managerMember);
        assertThat(managerOptional).isNotPresent();
    }

    @Test
    @Transactional
    @DisplayName("프로모션 ID로 매니저 목록을 조회합니다.")
    void getManagersByPromotionId() {
        // given
        managerCommandService.createManager(promotion.getId(), managerMember);

        // when
        List<Member> managers = managerQueryService.getManagers(promotion.getId());

        // then
        assertThat(managers).isNotEmpty();
    }

    @Test
    @Transactional
    @DisplayName("게스트의 입장을 완료합니다.")
    void completeEntrance() {
        // given - guest is already created in setUp method
        // when
        managerCommandService.completeEntrance(guest.getId(), managerMember);

        // then
        Optional<Guest> updatedGuestOptional = guestRepository.findById(guest.getId());
        assertThat(updatedGuestOptional).isPresent();
        assertThat(updatedGuestOptional.get().getIsEntered()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("게스트의 예약을 취소합니다.")
    void cancelReservation() {
        // given - guest is already created in setUp method
        // when
        managerCommandService.cancelReservation(guest.getId(), managerMember);

        // then
        Optional<Guest> updatedGuestOptional = guestRepository.findById(guest.getId());
        assertThat(updatedGuestOptional).isPresent();
        assertThat(updatedGuestOptional.get().getIsReservationCancelled()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("게스트의 예약을 확정합니다.")
    void confirmReservation() {
        // given - guest is already created in setUp method
        // when
        managerCommandService.confirmReservation(guest.getId(), managerMember);

        // then
        Optional<Guest> updatedGuestOptional = guestRepository.findById(guest.getId());
        assertThat(updatedGuestOptional).isPresent();
        assertThat(updatedGuestOptional.get().getIsReservationConfirmed()).isTrue();
    }
}