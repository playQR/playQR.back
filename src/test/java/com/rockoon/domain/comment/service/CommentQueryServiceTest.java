package com.rockoon.domain.comment.service;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.comment.dto.CommentRequest;
import com.rockoon.domain.comment.repository.CommentRepository;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.config.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class CommentQueryServiceTest {
    //Service
    @Autowired
    CommentQueryService commentQueryService;
    @Autowired
    CommentCommandService commentCommandService;
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    MemberCommandService memberCommandService;
    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    //Repository
    @Autowired
    CommentRepository commentRepository;
    //Entity & dto & else
    Long promotionId;

    @BeforeEach
    void setUp() {
        List<MemberRegisterDto> saveList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            saveList.add(MemberRegisterDto.builder()
                    .name(String.valueOf(i))
                    .profileImg(String.valueOf(i))
                    .nickname(String.valueOf(i))
                    .nickname(String.valueOf(i))
                    .build());
        }
        List<Member> collect = saveList.stream()
                .map(memberCommandService::registerMember)
                .map(memberQueryService::getByMemberId)
                .collect(Collectors.toList());
        promotionId = promotionCommandService.createPromotion(collect.get(0), new PromotionRequest());
        collect.forEach(member -> commentCommandService.createComment(member, promotionId, new CommentRequest()));
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("")
    void testMethodName() {
        //given

        //when

        //then
    }


}