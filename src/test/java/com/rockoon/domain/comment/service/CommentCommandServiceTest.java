package com.rockoon.domain.comment.service;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.comment.dto.CommentRequest;
import com.rockoon.domain.comment.entity.Comment;
import com.rockoon.domain.comment.repository.CommentRepository;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.config.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CommentCommandServiceTest {
    //Service
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
        MemberRegisterDto saveMember = MemberRegisterDto.builder()
                .kakaoEmail("kakaoEmail")
                .nickname("nickname")
                .profileImg("profileImg")
                .name("name")
                .build();
        Long memberId = memberCommandService.registerMember(saveMember);
        Member writer = memberQueryService.getByMemberId(memberId);
        PromotionRequest request = PromotionRequest.builder()
                .title("title")
                .content("content")
                .team("team")
                .maxAudience(30)
                .build();
        promotionId = promotionCommandService.createPromotion(writer, request);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("프로모션의 댓글을 작성합니다.")
    void createCommentOfPromotion() {
        //given
        MemberRegisterDto saveMember = MemberRegisterDto.builder()
                .kakaoEmail("newlykakaoEmail")
                .nickname("newlynickname")
                .profileImg("newlyprofileImg")
                .name("newlyname")
                .build();
        CommentRequest request = CommentRequest.builder()
                .content("content")
                .build();
        Long memberId = memberCommandService.registerMember(saveMember);
        Member commentWriter = memberQueryService.getByMemberId(memberId);
        //when
        Long commentId = commentCommandService.createComment(commentWriter, promotionId, request);
        //then
        Comment comment = commentRepository.findById(commentId).get();
        assertThat(comment.getContent()).isEqualTo(request.getContent());
    }


}