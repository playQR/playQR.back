package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.board.service.promotion.PromotionQueryService;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.entity.Role;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.team.service.TeamCommandService;
import com.rockoon.global.config.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PromotionQueryServiceTest {
    //service
    @Autowired
    PromotionQueryService promotionQueryService;
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    TeamCommandService teamCommandService;
    //repository
    @Autowired
    MemberRepository memberRepository;
    //else(bean)
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    //entity & Dto
    Member member1;
    Member member2;
    Member member3;


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
        member3 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao2@naver.com")
                .username("Hannw")
                .nickname("HHH")
                .build();
        memberRepository.saveAll(List.of(member1, member2, member3));
        savePromotion(5, member1);
        savePromotion(5, member2);
        savePromotion(5, member3);
    }

    private void savePromotion(int count, Member member) {
        for (int i = 0; i < count; i++) {
            promotionCommandService.savePromotion(member,
                    PromotionRequest.builder()
                            .title("title" + i)
                            .content("content")
                            .build()
            );
        }
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("작성된 모든 promotion을 가져옵니다.")
    void getAllPromotion() {
        //given

        //when
        List<Promotion> all = promotionQueryService.getAll();
        //then
        assertThat(all)
                .hasSize(15);
    }

    @Test
    @DisplayName("등록된 모든 promotion을 최신순으로 가져옵니다.")
    void getAllPromotionByLatest() {
        //given

        //when
        List<Promotion> allByLatest = promotionQueryService.getAllByLatest();
        //then

        for (int i = 1; i < allByLatest.size(); i++) {
            log.info("time = {}", allByLatest.get(i).getCreatedDate());
            assertThat(allByLatest.get(i).getCreatedDate())
                    .isAfterOrEqualTo(allByLatest.get(i - 1).getCreatedDate());
        }

    }

    @Test
    @DisplayName("페이지네이션을 통해 글을 가장 오래된 순으로, 그리고 10개씩 잘라 가져옵니다.")
    void getAllPromotionByPagination() {
        //given

        //when
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdDate").descending());
        Page<Promotion> paginationPromotion = promotionQueryService.getPaginationPromotion(pageable);
        //then
        assertThat(paginationPromotion.getSize()).isEqualTo(10);
        assertThat(paginationPromotion.getTotalPages()).isEqualTo(2);
        for (int i = 1; i < paginationPromotion.getSize(); i++) {
            log.info("time = {}", paginationPromotion.getContent().get(i).getCreatedDate());
            assertThat(paginationPromotion.getContent().get(i).getCreatedDate())
                    .isBeforeOrEqualTo(paginationPromotion.getContent().get(i - 1).getCreatedDate());
        }

    }
}