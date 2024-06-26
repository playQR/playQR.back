package com.bandit.domain.comment.service;

import com.bandit.domain.comment.dto.CommentRequest;
import com.bandit.domain.member.entity.Member;

public interface CommentCommandService {

    Long createComment(Member member, Long promotionId, CommentRequest request);

    void removeComment(Member member, Long commentId);
}
