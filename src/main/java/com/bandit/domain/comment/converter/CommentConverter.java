package com.bandit.domain.comment.converter;

import com.bandit.domain.comment.dto.CommentResponse.CommentListDto;
import com.bandit.domain.comment.dto.CommentResponse.CommentViewDto;
import com.bandit.domain.comment.entity.Comment;
import com.bandit.domain.member.converter.MemberConverter;
import com.bandit.global.util.PageUtil;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {
    public static CommentViewDto toViewDto(Comment comment) {
        return CommentViewDto.builder()
                .createdTime(comment.getCreatedDate())
                .memberResponse(MemberConverter.toResponse(comment.getWriter()))
                .content(comment.getContent())
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
                .build();
    }
}
