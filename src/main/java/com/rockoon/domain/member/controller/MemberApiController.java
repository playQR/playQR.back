package com.rockoon.domain.member.controller;

import com.rockoon.domain.member.converter.MemberConverter;
import com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.dto.MemberResponse;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.annotation.auth.AuthUser;
import com.rockoon.presentation.payload.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping
    public ApiResponseDto<Long> registerMember(@RequestBody MemberRegisterDto memberRegisterDto) {
        return ApiResponseDto.onSuccess(memberCommandService.registerMember(memberRegisterDto));
    }

    @PutMapping
    public ApiResponseDto<Long> modifyMemberInfo(@AuthUser Member member,
                                                 @RequestBody MemberModifyDto memberModifyDto) {
        log.info("member = {}", member.getNickname());
        return ApiResponseDto.onSuccess(memberCommandService.modifyMemberInfo(member, memberModifyDto));
    }

    @GetMapping("/{memberId}")
    public ApiResponseDto<MemberResponse> getMemberInfo(@PathVariable Long memberId) {
        return ApiResponseDto.onSuccess(
                MemberConverter.toResponse(
                        memberQueryService.getByMemberId(memberId)
                )
        );
    }
}
