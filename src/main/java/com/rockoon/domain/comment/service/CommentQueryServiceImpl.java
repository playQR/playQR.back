package com.rockoon.domain.comment.service;

import com.rockoon.domain.comment.entity.Comment;
import com.rockoon.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@RequiredArgsConstructor
@Service
public class CommentQueryServiceImpl implements CommentQueryService{
    private final CommentRepository commentRepository;
    @Override
    public Page<Comment> getPaginationCommentByPromotionId(Long promotionId, Pageable pageable) {
        commentRepository.findByPromotionId(promotionId, pageable);
        return null;
    }
}
