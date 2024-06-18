package com.rockoon.domain.comment.repository;

import com.rockoon.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPromotionId(Long promotionId, Pageable pageable);
}
