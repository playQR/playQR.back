package com.bandit.domain.ticket.controller;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.converter.GuestConverter;
import com.bandit.domain.ticket.dto.guest.GuestResponse.GuestListDto;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.service.guest.GuestQueryService;
import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.global.annotation.auth.AuthUser;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bandit.global.annotation.api.PredefinedErrorStatus.AUTH;

@Tag(name = "Guest API V2", description = "게스트 관련 API V2")
@ApiResponse(responseCode = "2000", description = "성공")
@RestController
@RequestMapping("/api/v2/guests")
@RequiredArgsConstructor
public class GuestApiV2Controller {
    private final GuestQueryService guestQueryService;

    @Operation(summary = "예약 명단 검색 🔑", description = "호스트가 가지는 특정 게시글의 예약 명단에서 검색을 합니다" +
            "default : list(전체 검색)" +
            "specific : list(예약자 명 검색 결과 리스트)")
    @ApiErrorCodeExample(status = AUTH)
    @GetMapping("/{promotionId}/reservation/search")
    public ApiResponseDto<GuestListDto> searchReservation(@AuthUser Member member,
                                                          @PathVariable Long promotionId,
                                                          @RequestParam(required = false) String name) {
        List<Guest> guests = guestQueryService.findGuestsByName(promotionId, name, member);
        return ApiResponseDto.onSuccess(GuestConverter.toListDto(guests));
    }
}
