package com.bandit.domain.like.service.music_like;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.board.service.promotion.PromotionCommandService;
import com.bandit.domain.board.service.promotion.PromotionQueryService;
import com.bandit.domain.like.repository.LikeMusicRepository;
import com.bandit.domain.member.dto.MemberRequest;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.service.MemberCommandService;
import com.bandit.domain.member.service.MemberQueryService;
import com.bandit.domain.music.dto.MusicRequest;
import com.bandit.domain.music.entity.PromotionMusic;
import com.bandit.domain.music.repository.PromotionMusicRepository;
import com.bandit.global.config.test.DatabaseCleanUp;
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

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class LikeMusicQueryServiceTest {
    //Service
    @Autowired
    LikeMusicCommandService likeMusicCommandService;
    @Autowired
    LikeMusicQueryService likeMusicQueryService;
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
    LikeMusicRepository likeMusicRepository;
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    PromotionMusicRepository promotionMusicRepository;
    //Entity & dto & else
    Member hostMember;
    Member guestMember;
    Long promotionId;


    @BeforeEach
    void setup() {
        MemberRequest.MemberRegisterDto hostDto = MemberRequest.MemberRegisterDto.builder()
                .nickname("hostNickname")
                .profileImg("hostProfile")
                .kakaoEmail("hostKakaoEmail")
                .name("host")
                .build();
        MemberRequest.MemberRegisterDto guestDto = MemberRequest.MemberRegisterDto.builder()
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
        promotionId = promotionCommandService.createPromotion(hostMember, request);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @Transactional
    @DisplayName("사용자가 해당 셑리스트에 좋아요를 눌렀는지 확인합니다.")
    void checkLikeIt() {
        //given
        Promotion promotion = promotionRepository.findById(promotionId).get();
        List<PromotionMusic> promotionMusicList = promotion.getPromotionMusicList();
        likeMusicCommandService.likeMusic(promotionMusicList.get(0).getId(), guestMember);
        //when
        boolean liked0 = likeMusicQueryService.isLiked(promotionMusicList.get(0).getId(), guestMember);
        boolean liked1 = likeMusicQueryService.isLiked(promotionMusicList.get(1).getId(), guestMember);
        //then
        assertThat(liked0).isTrue();
        assertThat(liked1).isFalse();
    }

    @Test
    @Transactional
    @DisplayName("해당 노래의 좋아요 개수를 조회합니다.")
    void countLikeMusic() {
        //given
        Promotion promotion = promotionRepository.findById(promotionId).get();
        List<PromotionMusic> promotionMusicList = promotion.getPromotionMusicList();
        likeMusicCommandService.likeMusic(promotionMusicList.get(0).getId(), guestMember);
        //when
        long count = likeMusicQueryService.countLike(promotionMusicList.get(0).getId());
        //then
        assertThat(count).isOne();
    }

}