package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.domain.board.promotion.repository.PromotionRepository;
import com.rockoon.domain.image.entity.Image;
import com.rockoon.domain.image.repository.ImageRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.entity.Role;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.music.entity.Music;
import com.rockoon.domain.music.repository.MusicRepository;
import com.rockoon.domain.option.entity.Category;
import com.rockoon.domain.option.entity.Option;
import com.rockoon.domain.option.repository.OptionRepository;
import com.rockoon.global.test.DatabaseCleanUp;
import com.rockoon.web.dto.image.ImageRequest;
import com.rockoon.web.dto.music.MusicRequest;
import com.rockoon.web.dto.option.OptionRequest;
import com.rockoon.web.dto.promotion.PromotionRequest;
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
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    MusicRepository musicRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    Member member1;
    List<OptionRequest> optionList = new ArrayList<>();

    List<ImageRequest> imageList = new ArrayList<>();
    List<MusicRequest> musicList = new ArrayList<>();

    PromotionRequest request;

    @BeforeEach
    void registerMember() {
        member1 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao@naver.com")
                .username("hann")
                .position("guitar")
                .nickname("hann")
                .build();
        memberRepository.save(member1);
        optionList.add(OptionRequest.builder()
                .category(Category.TIME)
                .content("content")
                .build());
        optionList.add(OptionRequest.builder()
                .category(Category.FEE)
                .content("content")
                .build());
        imageList.add(ImageRequest.builder()
                .imageUrl("image.png")
                .build());
        List<String> youtubeUrlList = new ArrayList<>();
        youtubeUrlList.add("https://www.youtube.com/watch?v=A0yntW5zRg8&list=RDA0yntW5zRg8&start_radio=1");
        musicList.add(MusicRequest.builder()
                .artist("thornApple")
                .title("blueSpring")
                .youtubeUrlList(youtubeUrlList)
                .build());
        musicList.add(MusicRequest.builder()
                .artist("silicagel")
                .title("tiktaktok")
                .youtubeUrlList(youtubeUrlList)
                .build());

    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("promotion글을 작성한다. 연관 엔티티 작성 제외")
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
    @DisplayName("promotion글을 작성한다. 연관 엔티티 포함(image, option, music)")
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
        List<Option> optionsByBoardId = optionRepository.findOptionsByBoardId(promotionId);
        List<Image> imagesByBoardId = imageRepository.findImagesByBoardId(promotionId);
        List<Music> musicsByBoardId = musicRepository.findMusicsByPromotionId(promotionId);
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
        optionList.add(OptionRequest.builder().build());
        PromotionRequest updateRequest = PromotionRequest.builder()
                .title("promotion update test")
                .content("promotion")
                .imageList(imageList)
                .optionList(optionList)
                .musicList(musicList)
                .maxAudience(3)
                .build();

        //when
        Long updatePromotionId = promotionCommandService.updatePromotion(member1, promotionId, updateRequest);
        //then
        Promotion promotion = promotionRepository.findById(updatePromotionId).get();
        log.info("title is {}", promotion.getTitle());
        assertThat(promotion.getTitle()).isEqualTo(updateRequest.getTitle());
        List<Option> optionsByBoardId = optionRepository.findOptionsByBoardId(updatePromotionId);
        List<Image> imagesByBoardId = imageRepository.findImagesByBoardId(updatePromotionId);
        List<Music> musicsByBoardId = musicRepository.findMusicsByPromotionId(updatePromotionId);
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
        assertThatThrownBy(()->promotionCommandService.updatePromotion(member1, 1L, updateRequest))
                .isInstanceOf(RuntimeException.class);

    }
}