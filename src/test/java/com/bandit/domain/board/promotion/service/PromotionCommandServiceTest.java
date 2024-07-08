package com.bandit.domain.board.promotion.service;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.board.service.promotion.PromotionCommandService;
import com.bandit.domain.image.entity.Image;
import com.bandit.domain.image.repository.ImageRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.entity.Role;
import com.bandit.domain.member.repository.MemberRepository;
import com.bandit.domain.music.dto.MusicRequest;
import com.bandit.domain.music.entity.Music;
import com.bandit.domain.music.repository.MusicRepository;
import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.repository.TicketRepository;
import com.bandit.global.config.test.DatabaseCleanUp;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.PromotionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class PromotionCommandServiceTest {
    //command Service
    @Autowired
    PromotionCommandService promotionCommandService;
    //repository
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    MusicRepository musicRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    MemberRepository memberRepository;
    //else
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    //entity & requestDto
    Member member1;
    Member member2;
    Long teamId;
    List<String> imageList = new ArrayList<>();
    List<MusicRequest> musicList = new ArrayList<>();

    PromotionRequest request;
    @BeforeEach
    void setUp() {
        member1 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao@naver.com")
                .username("hann")
                .nickname("hann")
                .build();
        member2 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao1@naver.com")
                .username("Hann")
                .nickname("Hann")
                .build();

        imageList.add("image.png");
        musicList.add(MusicRequest.builder()
                .artist("thornApple")
                .title("blueSpring")
                .build());
        musicList.add(MusicRequest.builder()
                .artist("silicagel")
                .title("tiktaktok")
                .build());

        memberRepository.save(member1);
        memberRepository.save(member2);

    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("유저가 promotion글을 작성한다. 연관 엔티티 작성 제외")
    void createPromotionWithoutRelationEntity() {
        //given
        request = PromotionRequest.builder()
                .entranceFee(7000)
                .title("promotion test")
                .content("promotion")
                .maxAudience(3)
                .refundInfo("refund_info")
                .account("04110790601014")
                .accountHolder("이정한")
                .bankName("ibk")
                .showDate(LocalDate.of(2024, 8,2))
                .showLocation("001 club")
                .startTime("07:00")
                .endTime("09:00")
                .build();
        //when
        Long promotionId = promotionCommandService.createPromotion(member1, request);

        //then
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        assertThat(promotionOptional).isPresent()
                .get()
                .extracting("title", "content", "maxAudience", "refundInfo",
                        "account", "accountHolder", "bankName", "showLocation",
                        "startTime", "endTime", "showDate", "entranceFee")
                .containsExactly(request.getTitle(), request.getContent(), request.getMaxAudience(), request.getRefundInfo(),
                        request.getAccount(), request.getAccountHolder(), request.getBankName(), request.getShowLocation(),
                        request.getStartTime(), request.getEndTime(), request.getShowDate(), request.getEntranceFee());
    }

    @Test
    @DisplayName("유저가 promotion글을 작성한다. 연관 엔티티 포함(image, option, music)")
    void createPromotionWithRelationEntity() {
        //given
        request = PromotionRequest.builder()
                .title("promotion test")
                .content("promotion")
                .imageList(imageList)
                .musicList(musicList)
                .maxAudience(3)
                .build();

        //when
        Long promotionId = promotionCommandService.createPromotion(member1, request);

        //then
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        List<Image> imagesByBoardId = imageRepository.findImagesByBoardId(promotionId);
        List<Music> musicsByBoardId = musicRepository.findByPromotionId(promotionId);
        assertThat(imagesByBoardId).hasSize(1);
        assertThat(musicsByBoardId).hasSize(2);
        assertThat(promotionOptional.get().getTicket().getDueDate())
                .isEqualTo(request.getShowDate());

    }

    @Test
    @DisplayName("등록된 Promotion을 연관 데이터와 함께 수정합니다.")
    void updatePromotion() {
        //given
        request = PromotionRequest.builder()
                .title("promotion test")
                .content("promotion")
                .imageList(imageList)
                .musicList(musicList)
                .showDate(LocalDate.of(2024,8,2))
                .maxAudience(3)
                .build();
        Long promotionId = promotionCommandService.createPromotion(member1, request);
        imageList.remove(0);
        PromotionRequest updateRequest = PromotionRequest.builder()
                .title("promotion update test")
                .content("promotion")
                .imageList(imageList)
                .musicList(musicList)
                .showDate(LocalDate.of(2024,9,3))
                .maxAudience(3)
                .build();

        //when
        Long updatePromotionId = promotionCommandService.modifyPromotion(member1, promotionId, updateRequest);
        //then
        Promotion promotion = promotionRepository.findById(updatePromotionId).get();
        assertThat(promotion.getTitle()).isEqualTo(updateRequest.getTitle());
        List<Image> imagesByBoardId = imageRepository.findImagesByBoardId(updatePromotionId);
        List<Music> musicsByBoardId = musicRepository.findByPromotionId(updatePromotionId);
        assertThat(imagesByBoardId).hasSize(0);
        assertThat(musicsByBoardId).hasSize(2);
        assertThat(promotion.getTicket().getDueDate()).isEqualTo(updateRequest.getShowDate());
    }

    @Test
    @DisplayName("등록되지 않은 Promotion을 수정 할 때, board를 찾는 데 실패하는 예외를 확인합니다.")
    void executeExceptionWhenUpdatePromotion() {
        //given
        PromotionRequest updateRequest = PromotionRequest.builder()
                .title("promotion update test")
                .content("promotion")
                .imageList(imageList)
                .musicList(musicList)
                .maxAudience(3)
                .build();

        //when & then
        assertThatThrownBy(() -> promotionCommandService.modifyPromotion(member1, 0L, updateRequest))
                .isInstanceOf(PromotionHandler.class)
                .hasMessage(ErrorStatus.PROMOTION_NOT_FOUND.getMessage());

    }

    @Test
    @DisplayName("등록된 promotion을 지우고 db의 결과를 확인합니다.")
    void deletePromotion() {
        //given
        PromotionRequest createRequest = PromotionRequest.builder()
                .title("promotion update test")
                .content("promotion")
                .imageList(imageList)
                .musicList(musicList)
                .maxAudience(3)
                .build();
        Long promotionId = promotionCommandService.createPromotion(member1, createRequest);
        //when
        log.info("db = {}", memberRepository.findById(member1.getId()).get());
        promotionCommandService.removePromotion(member1, promotionId);
        //then
        List<Promotion> all = promotionRepository.findAll();
        List<Ticket> allTicket = ticketRepository.findAll();
        assertThat(all).hasSize(0);
        assertThat(allTicket).hasSize(0);
    }
    @Test
    @DisplayName("등록된 promotion을 지울 때, 작성자가 아닐 경우에 예외를 확인합니다.")
    void executeExceptionWhenRemovePromotion() {
        //given
        PromotionRequest createRequest = PromotionRequest.builder()
                .title("promotion update test")
                .content("promotion")
                .imageList(imageList)
                .musicList(musicList)
                .maxAudience(3)
                .build();
        Long promotionId = promotionCommandService.createPromotion(member1, createRequest);
        //when
        assertThatThrownBy(() -> promotionCommandService.removePromotion(member2, promotionId))
                .isInstanceOf(PromotionHandler.class)
                .hasMessage(ErrorStatus.PROMOTION_ONLY_CAN_BE_TOUCHED_BY_WRITER.getMessage());
    }
}