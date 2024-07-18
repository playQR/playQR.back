package com.bandit.domain.comment.service;

import com.bandit.domain.comment.entity.Comment;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CommentQueryService {

    Page<Comment> getPaginationCommentByPromotionId(Long promotionId, Pageable pageable);

    List<Comment> getPaginationCommentsByWriter(Member member);
}
