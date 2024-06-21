package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.repository.PromotionRepository;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.image.entity.Image;
import com.rockoon.domain.image.repository.ImageRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.entity.Role;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.music.dto.MusicRequest;
import com.rockoon.domain.music.entity.PromotionMusic;
import com.rockoon.domain.music.repository.MusicRepository;
import com.rockoon.domain.music.repository.PromotionMusicRepository;
import com.rockoon.domain.showOption.dto.ShowOptionRequest;
import com.rockoon.domain.showOption.entity.Category;
import com.rockoon.domain.showOption.entity.ShowOption;
import com.rockoon.domain.showOption.repository.showOptionRepository;
import com.rockoon.global.config.test.DatabaseCleanUp;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.PromotionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
    showOptionRepository showOptionRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    MusicRepository musicRepository;
    @Autowired
    PromotionMusicRepository promotionMusicRepository;
    @Autowired
    MemberRepository memberRepository;
    //else
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    //entity & requestDto
    Member member1;
    Member member2;
    Long teamId;
    List<ShowOptionRequest> optionList = new ArrayList<>();

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

        optionList.add(ShowOptionRequest.builder()
                .category(Category.TIME)
                .content("content")
                .build());
        optionList.add(ShowOptionRequest.builder()
                .category(Category.FEE)
                .content("content")
                .build());
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
                .title("promotion test")
                .content("promotion")
                .maxAudience(3)
                .build();
        //when
        Long promotionId = promotionCommandService.createPromotion(member1, request);

        //then
        Promotion promotion = promotionRepository.findById(promotionId).get();
        assertThat(promotion.getMaxAudience()).isEqualTo(request.getMaxAudience());
        assertThat(promotion.getContent()).isEqualTo(request.getContent());
        assertThat(promotion.getTitle()).isEqualTo(request.getTitle());
    }

    @Test
    @DisplayName("유저가 promotion글을 작성한다. 연관 엔티티 포함(image, option, music)")
    void createPromotionWithRelationEntity() {
        //given
        request = PromotionRequest.builder()
                .title("promotion test")
                .content("promotion")
                .imageList(imageList)
                .optionList(optionList)
                .musicList(musicList)
                .maxAudience(3)
                .build();

        //when
        Long promotionId = promotionCommandService.createPromotion(member1, request);

        //then
        List<ShowOption> optionsByBoardId = showOptionRepository.findOptionsByBoardId(promotionId);
        List<Image> imagesByBoardId = imageRepository.findImagesByBoardId(promotionId);
        List<PromotionMusic> musicsByBoardId = promotionMusicRepository.findMusicsByPromotionId(promotionId);
        assertThat(optionsByBoardId).hasSize(2);
        assertThat(imagesByBoardId).hasSize(1);
        assertThat(musicsByBoardId).hasSize(2);

    }

    @Test
    @DisplayName("등록된 Promotion을 연관 데이터와 함께 수정합니다.")
    void updatePromotion() {
        //given
        request = PromotionRequest.builder()
                .title("promotion test")
                .content("promotion")
                .imageList(imageList)
                .optionList(optionList)
                .musicList(musicList)
                .maxAudience(3)
                .build();
        Long promotionId = promotionCommandService.createPromotion(member1, request);
        imageList.remove(0);
        optionList.add(ShowOptionRequest.builder().build());
        PromotionRequest updateRequest = PromotionRequest.builder()
                .title("promotion update test")
                .content("promotion")
                .imageList(imageList)
                .optionList(optionList)
                .musicList(musicList)
                .maxAudience(3)
                .build();

        //when
        Long updatePromotionId = promotionCommandService.modifyPromotion(member1, promotionId, updateRequest);
        //then
        Promotion promotion = promotionRepository.findById(updatePromotionId).get();
        assertThat(promotion.getTitle()).isEqualTo(updateRequest.getTitle());
        List<ShowOption> optionsByBoardId = showOptionRepository.findOptionsByBoardId(updatePromotionId);
        List<Image> imagesByBoardId = imageRepository.findImagesByBoardId(updatePromotionId);
        List<PromotionMusic> musicsByBoardId = promotionMusicRepository.findMusicsByPromotionId(updatePromotionId);
        assertThat(optionsByBoardId).hasSize(3);
        assertThat(imagesByBoardId).hasSize(0);
        assertThat(musicsByBoardId).hasSize(2);
    }

    @Test
    @DisplayName("등록되지 않은 Promotion을 수정 할 때, board를 찾는 데 실패하는 예외를 확인합니다.")
    void executeExceptionWhenUpdatePromotion() {
        //given
        PromotionRequest updateRequest = PromotionRequest.builder()
                .title("promotion update test")
                .content("promotion")
                .imageList(imageList)
                .optionList(optionList)
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
                .optionList(optionList)
                .musicList(musicList)
                .maxAudience(3)
                .build();
        Long promotionId = promotionCommandService.createPromotion(member1, createRequest);
        //when
        log.info("db = {}", memberRepository.findById(member1.getId()).get());
        promotionCommandService.removePromotion(member1, promotionId);
        //then
        List<Promotion> all = promotionRepository.findAll();
        assertThat(all).hasSize(0);
    }
    @Test
    @DisplayName("등록된 promotion을 지울 때, 작성자가 아닐 경우에 예외를 확인합니다.")
    void executeExceptionWhenRemovePromotion() {
        //given
        PromotionRequest createRequest = PromotionRequest.builder()
                .title("promotion update test")
                .content("promotion")
                .imageList(imageList)
                .optionList(optionList)
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