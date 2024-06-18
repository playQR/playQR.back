package com.rockoon.domain.comment.controller;

import com.rockoon.domain.comment.dto.CommentRequest;
import com.rockoon.domain.comment.service.CommentCommandService;
import com.rockoon.domain.comment.service.CommentQueryService;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.global.annotation.api.ApiErrorCodeExample;
import com.rockoon.global.annotation.auth.AuthUser;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment API", description = "댓글 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @Operation(summary = "댓글 작성 🔑", description = "로그인한 회원이 프로모션(홍보글)에 댓글을 작성합니다.")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR,
            ErrorStatus._UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR,
            ErrorStatus._ASSIGNABLE_PARAMETER,
            ErrorStatus.MEMBER_NOT_FOUND
    })
    @PostMapping("/{promotionId}")
    public ApiResponseDto<Long> createComment(@AuthUser Member member,
                                              @PathVariable Long promotionId,
                                              @RequestBody CommentRequest commentRequest) {
        return ApiResponseDto.onSuccess(commentCommandService.createComment(member, promotionId, commentRequest));
    }
    @Operation(summary = "댓글 삭제 🔑", description = "작성자가 등록한 댓글을 삭제합니다")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR,
            ErrorStatus._UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR,
            ErrorStatus._ASSIGNABLE_PARAMETER,
            ErrorStatus.MEMBER_NOT_FOUND
    })
    @DeleteMapping("/{commentId}")
    public ApiResponseDto<Boolean> removeComment(@AuthUser Member member,
                                              @PathVariable Long commentId) {
        commentCommandService.removeComment(member, commentId);
        return ApiResponseDto.onSuccess(true);
    }
}
