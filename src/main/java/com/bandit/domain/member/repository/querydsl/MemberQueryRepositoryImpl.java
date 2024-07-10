package com.bandit.domain.member.repository.querydsl;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bandit.domain.comment.entity.QComment.comment;
import static com.bandit.domain.image.entity.QImage.image;
import static com.bandit.domain.like.entity.QLikeMusic.likeMusic;
import static com.bandit.domain.like.entity.QLikePromotion.likePromotion;
import static com.bandit.domain.ticket.entity.QGuest.guest;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepositoryImpl implements MemberQueryRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public void deleteWithRelations(Member member) {
        //board
        queryFactory.delete(image).where(image.board.writer.eq(member)).execute();
        //guest
        queryFactory.delete(guest).where(guest.member.eq(member)).execute();
        //likeMusic
        queryFactory.delete(likeMusic).where(likeMusic.member.eq(member)).execute();
        //likePromotion
        queryFactory.delete(likePromotion).where(likePromotion.member.eq(member)).execute();
        //manager

        //comment
        queryFactory.delete(comment).where(comment.writer.eq(member)).execute();
        //Member
        queryFactory.delete(QMember.member).where(QMember.member.eq(member)).execute();
    }
}
