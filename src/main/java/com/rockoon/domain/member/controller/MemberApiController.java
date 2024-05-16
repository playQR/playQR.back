package com.rockoon.domain.member.controller;

import com.rockoon.domain.member.converter.MemberConverter;
import com.rockoon.domain.member.dto.MemberRequest;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.dto.MemberResponse;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.presentation.payload.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{memberId}")          //TODO path -> memberId 삭제, jwt token을 사용해서 member 바로 조회
    public ApiResponseDto<Long> modifyMemberInfo(@PathVariable Long memberId,
                                 @RequestBody MemberRequest.MemberModifyDto memberModifyDto) {
        Member member = memberQueryService.getByMemberId(memberId);
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
