package com.bandit.domain.comment.service;

import com.bandit.domain.comment.entity.Comment;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentQueryService {

    Page<Comment> getPaginationCommentByPromotionId(Long promotionId, Pageable pageable);

    Page<Comment> getPaginationCommentsByWriter(Member member, Pageable pageable);
}
