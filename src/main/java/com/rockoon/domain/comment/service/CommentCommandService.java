package com.rockoon.domain.comment.service;

import com.rockoon.domain.comment.dto.CommentRequest;
import com.rockoon.domain.member.entity.Member;

public interface CommentCommandService {

    Long createComment(Member member, Long promotionId, CommentRequest request);

    void removeComment(Member member, Long commentId);
}
