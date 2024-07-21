package com.bandit.domain.comment.controller;

import com.bandit.domain.comment.converter.CommentConverter;
import com.bandit.domain.comment.dto.CommentRequest;
import com.bandit.domain.comment.dto.CommentResponse.CommentListDto;
import com.bandit.domain.comment.dto.CommentResponse.MyCommentListDto;
import com.bandit.domain.comment.service.CommentCommandService;
import com.bandit.domain.comment.service.CommentQueryService;
import com.bandit.domain.member.entity.Member;
import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.global.annotation.auth.AuthUser;
import com.bandit.global.util.PageUtil;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.bandit.global.annotation.api.PredefinedErrorStatus.AUTH;

@Tag(name = "Comment API", description = "댓글 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @Operation(summary = "댓글 작성 🔑", description = "로그인한 회원이 프로모션(홍보글)에 댓글을 작성합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.PROMOTION_NOT_FOUND
    }, status = AUTH)
    @PostMapping("/{promotionId}")
    public ApiResponseDto<Long> createComment(@AuthUser Member member,
                                              @PathVariable Long promotionId,
                                              @RequestBody CommentRequest commentRequest) {
        return ApiResponseDto.onSuccess(commentCommandService.createComment(member, promotionId, commentRequest));
    }
    @Operation(summary = "댓글 삭제 🔑", description = "작성자가 등록한 댓글을 삭제합니다")
    @ApiErrorCodeExample(value = {
            ErrorStatus.COMMENT_NOT_FOUND,
            ErrorStatus.COMMENT_CAN_BE_ONLY_TOUCHED_BY_WRITER
    }, status = AUTH)
    @DeleteMapping("/{commentId}")
    public ApiResponseDto<Boolean> removeComment(@AuthUser Member member,
                                                 @PathVariable Long commentId) {
        commentCommandService.removeComment(member, commentId);
        return ApiResponseDto.onSuccess(true);
    }
    @Operation(summary = "댓글 조회(페이징)", description = "프로모션에 등록된 댓글들을 조회합니다.")
    @ApiErrorCodeExample
    @GetMapping("/{promotionId}")
    public ApiResponseDto<CommentListDto> getComments(@PathVariable Long promotionId,
                                                        @RequestParam(defaultValue = "0") int currentPage) {
        Pageable pageable = PageRequest.of(currentPage, PageUtil.COMMENT_SIZE);
        return ApiResponseDto.onSuccess(CommentConverter.toListDto(
                commentQueryService.getPaginationCommentByPromotionId(promotionId, pageable)));
    }

    @Operation(summary = "나의 댓글 조회(페이징) 🔑", description = "로그인한 유저가 자신이 작성한 댓글들을 조회합니다.")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @GetMapping("/my")
    public ApiResponseDto<MyCommentListDto> getMyComments(@AuthUser Member member) {
        return ApiResponseDto.onSuccess(CommentConverter
                .toMyListDto(commentQueryService.getPaginationCommentsByWriter(member)));
    }
}
