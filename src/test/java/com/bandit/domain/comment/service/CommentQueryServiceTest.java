package com.bandit.domain.comment.service;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.board.service.promotion.PromotionCommandService;
import com.bandit.domain.comment.dto.CommentRequest;
import com.bandit.domain.comment.entity.Comment;
import com.bandit.domain.comment.repository.CommentRepository;
import com.bandit.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.service.MemberCommandService;
import com.bandit.domain.member.service.MemberQueryService;
import com.bandit.global.config.test.DatabaseCleanUp;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CommentQueryServiceTest {
    //Service
    @Autowired
    CommentQueryService commentQueryService;
    @Autowired
    CommentCommandService commentCommandService;
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
    CommentRepository commentRepository;
    //Entity & dto & else
    Long promotionId;

    @BeforeEach
    void setUp() {
        List<MemberRegisterDto> saveList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            saveList.add(MemberRegisterDto.builder()
                    .name(String.valueOf(i))
                    .profileImg(String.valueOf(i))
                    .kakaoEmail(String.valueOf(i))
                    .nickname(String.valueOf(i))
                    .nickname(String.valueOf(i))
                    .build());
        }
        List<Member> collect = saveList.stream()
                .map(memberCommandService::registerMember)
                .map(memberQueryService::getByMemberId)
                .collect(Collectors.toList());
        promotionId = promotionCommandService.createPromotion(collect.get(0), PromotionRequest.builder().content("content").build());
        collect.forEach(member -> commentCommandService.createComment(member, promotionId, new CommentRequest()));
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("프로모션 내에 작성된 댓글들을 페이징으로 가져옵니다.")
    void getPaginationCommentByPromotionId() {
        //given
        Pageable pageable = PageRequest.of(0, 5);
        //when
        Page<Comment> paginationComment = commentQueryService.getPaginationCommentByPromotionId(promotionId, pageable);
        //then
        assertThat(paginationComment.getSize()).isEqualTo(5);
    }


}