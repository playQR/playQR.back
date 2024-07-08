package com.bandit.domain.comment.repository;

import com.bandit.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPromotionId(Long promotionId, Pageable pageable);

    void deleteByPromotionId(Long promotionId);

}
