package com.rockoon.domain.comment.service;

import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.repository.PromotionRepository;
import com.rockoon.domain.comment.dto.CommentRequest;
import com.rockoon.domain.comment.entity.Comment;
import com.rockoon.domain.comment.repository.CommentRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.PromotionHandler;
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

    }
}
