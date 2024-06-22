package com.rockoon.domain.like.service;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.board.service.promotion.PromotionQueryService;
import com.rockoon.domain.like.entity.Like;
import com.rockoon.domain.like.repository.LikeRepository;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.domain.music.dto.MusicRequest;
import com.rockoon.domain.music.entity.PromotionMusic;
import com.rockoon.global.config.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class LikeCommandServiceTest {
    //Service
    @Autowired
    LikeCommandService likeCommandService;
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    PromotionQueryService promotionQueryService;
    @Autowired
    MemberCommandService memberCommandService;
    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    //Repository
    @Autowired
    LikeRepository likeRepository;
    //Entity & dto & else
    Member hostMember;
    Member guestMember;
    Promotion promotion;


    @BeforeEach
    void setup() {
        MemberRegisterDto hostDto = MemberRegisterDto.builder()
                .nickname("hostNickname")
                .profileImg("hostProfile")
                .kakaoEmail("hostKakaoEmail")
                .name("host")
                .build();
        MemberRegisterDto guestDto = MemberRegisterDto.builder()
                .nickname("guestNickname")
                .profileImg("guestProfile")
                .kakaoEmail("guestKakaoEmail")
                .name("guest")
                .build();
        Long hostId = memberCommandService.registerMember(hostDto);
        Long guestId = memberCommandService.registerMember(guestDto);

        hostMember = memberQueryService.getByMemberId(hostId);
        guestMember = memberQueryService.getByMemberId(guestId);

        List<MusicRequest> setList = new ArrayList<>();
        setList.add(MusicRequest.builder().title("title1").artist("artist1").build());
        setList.add(MusicRequest.builder().title("title2").artist("artist2").build());
        setList.add(MusicRequest.builder().title("title3").artist("artist3").build());

        PromotionRequest request = PromotionRequest.builder()
                .maxAudience(3)
                .content("content")
                .team("team")
                .title("title")
                .musicList(setList)
                .build();
        Long promotionId = promotionCommandService.createPromotion(hostMember, request);
        promotion = promotionQueryService.getPromotionById(promotionId);

    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("게스트가 셑리스트에 좋아요를 누릅니다.")
    void LikeSetList() {
        //given
        List<PromotionMusic> promotionMusicList = promotion.getPromotionMusicList();
        //when
        Long likeId = likeCommandService.likeMusic(promotionMusicList.get(0).getId(), guestMember);
        //then
        Optional<Like> optionalLike = likeRepository.findById(likeId);
        assertThat(optionalLike).isPresent();
    }

}