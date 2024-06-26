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
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.CommentHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    Member writer;

    @BeforeEach
    void setUp() {
        MemberRegisterDto saveMember = MemberRegisterDto.builder()
                .kakaoEmail("kakaoEmail")
                .nickname("nickname")
                .profileImg("profileImg")
                .name("name")
                .build();
        Long memberId = memberCommandService.registerMember(saveMember);
        writer = memberQueryService.getByMemberId(memberId);
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

    @Test
    @Transactional
    @DisplayName("작성된 프로모션 내 댓글을 삭제합니다.")
    void removeComment() {
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
        Long commentId = commentCommandService.createComment(commentWriter, promotionId, request);
        Comment comment = commentRepository.findById(commentId).get();
        assertThat(comment.getContent()).isEqualTo(request.getContent());
        //when
        commentCommandService.removeComment(commentWriter, commentId);
        //then
        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @DisplayName("댓글 삭제 시, 작성자가 아닌 경우에 예외 상황을 발생시킵니다.")
    void executeExceptionWhenNotWriterTryToRemoveComment() {
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
        Long commentId = commentCommandService.createComment(commentWriter, promotionId, request);

        //when & then
        assertThatThrownBy(() -> commentCommandService.removeComment(writer, commentId))
                .isInstanceOf(CommentHandler.class)
                .hasMessage(ErrorStatus.COMMENT_CAN_BE_ONLY_TOUCHED_BY_WRITER.getMessage());
    }


}