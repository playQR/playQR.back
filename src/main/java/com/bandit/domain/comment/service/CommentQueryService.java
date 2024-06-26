package com.bandit.domain.comment.service;

import com.bandit.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentQueryService {

    Page<Comment> getPaginationCommentByPromotionId(Long promotionId, Pageable pageable);
}
