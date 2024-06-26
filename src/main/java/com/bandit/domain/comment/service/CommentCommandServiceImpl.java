package com.bandit.domain.comment.service;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.comment.dto.CommentRequest;
import com.bandit.domain.comment.entity.Comment;
import com.bandit.domain.comment.repository.CommentRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.CommentHandler;
import com.bandit.presentation.payload.exception.PromotionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentCommandServiceImpl implements CommentCommandService{

    private final CommentRepository commentRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public Long createComment(Member member, Long promotionId, CommentRequest request) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        Comment comment = commentRepository.save(Comment.of(member, promotion, request));
        return comment.getId();
    }

    @Override
    public void removeComment(Member member, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentHandler(ErrorStatus.COMMENT_NOT_FOUND));
        validateWriterOfComment(member, comment);
        commentRepository.delete(comment);
    }

    private static void validateWriterOfComment(Member member, Comment comment) {
        if (!comment.getWriter().equals(member)) {
            throw new CommentHandler(ErrorStatus.COMMENT_CAN_BE_ONLY_TOUCHED_BY_WRITER);
        }
    }
}
