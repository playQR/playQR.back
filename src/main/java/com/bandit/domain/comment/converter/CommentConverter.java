package com.bandit.domain.comment.converter;

import com.bandit.domain.comment.dto.CommentResponse.CommentListDto;
import com.bandit.domain.comment.dto.CommentResponse.CommentViewDto;
import com.bandit.domain.comment.dto.CommentResponse.MyCommentListDto;
import com.bandit.domain.comment.dto.CommentResponse.MyCommentViewDto;
import com.bandit.domain.comment.entity.Comment;
import com.bandit.domain.member.converter.MemberConverter;
import com.bandit.global.util.PageUtil;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {
    public static CommentViewDto toViewDto(Comment comment) {
        return CommentViewDto.builder()
                .id(comment.getId())
                .createdTime(comment.getCreatedDate())
                .memberResponse(MemberConverter.toResponse(comment.getWriter()))
                .content(comment.getContent())
                .build();
    }

    public static MyCommentViewDto toMyViewDto(Comment comment) {
        return MyCommentViewDto.builder()
                .id(comment.getId())
                .promotionId(comment.getPromotion().getId())
                .content(comment.getContent())
                .createdTime(comment.getCreatedDate())
                .build();
    }

    public static CommentListDto toListDto(Page<Comment> commentPage) {
        //TODO custom for other field (request from front-end)
        List<CommentViewDto> collect = commentPage.getContent().stream()
                .map(CommentConverter::toViewDto)
                .collect(Collectors.toList());
        return CommentListDto.builder()
                .commentList(collect)
                .nextPageParam(PageUtil.getNextPageParam(commentPage))
                .totalCount(commentPage.getTotalElements())
                .build();
    }
    public static MyCommentListDto toMyListDto(Page<Comment> commentPage) {
        //TODO custom for other field (request from front-end)
        List<MyCommentViewDto> collect = commentPage.getContent().stream()
                .map(CommentConverter::toMyViewDto)
                .collect(Collectors.toList());
        return MyCommentListDto.builder()
                .commentList(collect)
                .nextPageParam(PageUtil.getNextPageParam(commentPage))
                .totalCount(commentPage.getTotalElements())
                .build();
    }

}
