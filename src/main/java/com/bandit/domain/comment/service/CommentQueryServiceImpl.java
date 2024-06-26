package com.bandit.domain.comment.service;

import com.bandit.domain.comment.entity.Comment;
import com.bandit.domain.comment.repository.CommentRepository;
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
