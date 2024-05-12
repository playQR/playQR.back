package com.rockoon.domain.member.controller;

import com.rockoon.domain.member.dto.MemberRequest;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping
    public void registerMember(@RequestBody MemberRegisterDto memberRegisterDto) {
        //TODO return memberId
        memberCommandService.registerMember(memberRegisterDto);
    }

    @PutMapping("/{memberId}")          //TODO path -> memberId 삭제, jwt token을 사용해서 member 바로 조회
    public void modifyMemberInfo(@PathVariable Long memberId,
                                 @RequestBody MemberRequest.MemberModifyDto memberModifyDto) {
        Member member = memberQueryService.getByMemberId(memberId);
        memberCommandService.modifyMemberInfo(member, memberModifyDto);
    }

    @GetMapping("/{memberId}")
    public Member getMemberInfo(@PathVariable Long memberId) {
        return memberQueryService.getByMemberId(memberId);
    }
}
