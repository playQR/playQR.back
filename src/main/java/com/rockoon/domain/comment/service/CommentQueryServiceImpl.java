package com.rockoon.domain.comment.service;

import com.rockoon.domain.comment.entity.Comment;
import com.rockoon.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CommentQueryServiceImpl implements CommentQueryService{
    private final CommentRepository commentRepository;
    @Override
    public Page<Comment> getPaginationCommentByPromotionId(Long promotionId, Pageable pageable) {
        return commentRepository.findByPromotionId(promotionId, pageable);
    }
}
