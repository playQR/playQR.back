package com.bandit.domain.member.repository.querydsl;

import com.bandit.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bandit.domain.board.entity.QPromotion.promotion;
import static com.bandit.domain.comment.entity.QComment.comment;
import static com.bandit.domain.image.entity.QImage.image;
import static com.bandit.domain.like.entity.QLikeMusic.likeMusic;
import static com.bandit.domain.music.entity.QMusic.music;
import static com.bandit.domain.ticket.entity.QGuest.guest;
import static com.bandit.domain.ticket.entity.QTicket.ticket;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepositoryImpl implements MemberQueryRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public void deleteWithRelations(Member member) {
        //board
        queryFactory.delete(image).where(image.board.writer.eq(member)).execute();
        //promotion
        queryFactory.delete(likeMusic).where(likeMusic.music.promotion.writer.eq(member)).execute();
        queryFactory.delete(music).where(music.promotion.writer.eq(member)).execute();
        queryFactory.delete(ticket).where(ticket.promotion.writer.eq(member)).execute();
        queryFactory.delete(guest).where(guest.promotion.writer.eq(member)).execute();
        queryFactory.delete(comment).where(comment.promotion.writer.eq(member)).execute();
        //TODO remove manager
        queryFactory.delete(promotion).where(promotion.writer.eq(member)).execute();
        //guest
        queryFactory.delete(guest).where(guest.member.eq(member)).execute();
        //likeMusic
        queryFactory.delete(likeMusic).where(likeMusic.member.eq(member)).execute();
        //likePromotion

        //manager

        //comment
        queryFactory.delete(comment).where(comment.writer.eq(member)).execute();
    }
}
