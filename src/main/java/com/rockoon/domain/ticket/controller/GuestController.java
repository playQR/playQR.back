package com.rockoon.domain.ticket.controller;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.ticket.dto.guest.GuestRequest;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.service.guest.GuestCommandService;
import com.rockoon.domain.ticket.service.guest.GuestQueryService;
import com.rockoon.global.annotation.api.ApiErrorCodeExample;
import com.rockoon.global.annotation.auth.AuthUser;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Guest API", description = "게스트 관련 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestCommandService guestCommandService;
    private final GuestQueryService guestQueryService;

    @Operation(summary = "게스트 생성", description = "프로모션 ID와 멤버 정보, 이름을 받아 새로운 게스트를 생성합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @PostMapping
    public ApiResponseDto<Long> createGuest(
            @RequestParam Long promotionId,
            @AuthUser Member member,  // @AuthUser 사용
            @RequestParam String guestname) {
        Long guestId = guestCommandService.createGuest(promotionId, member, guestname);
        return ApiResponseDto.onSuccess(guestId);
    }

    @Operation(summary = "게스트 수정", description = "게스트 ID와 게스트 정보를 받아 기존 게스트 정보를 수정합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @PutMapping("/{guestId}")
    public ApiResponseDto<Void> updateGuest(
            @PathVariable Long guestId,
            @AuthUser Member member,
            @RequestBody @Valid GuestRequest.GuestModifyDto guestDetails) {
        guestCommandService.updateGuest(guestId, member, guestDetails);
        return ApiResponseDto.onSuccess(null);
    }

    @Operation(summary = "게스트 삭제", description = "게스트 ID를 받아 해당 게스트를 삭제합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @DeleteMapping("/{guestId}")
    public ApiResponseDto<Void> deleteGuest(@PathVariable Long guestId) {
        guestCommandService.deleteGuest(guestId);
        return ApiResponseDto.onSuccess(null);
    }

    @Operation(summary = "모든 게스트 조회", description = "모든 게스트 정보를 조회합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping
    public ApiResponseDto<List<Guest>> getAllGuests() {
        List<Guest> guests = guestQueryService.findAllGuests();
        return ApiResponseDto.onSuccess(guests);
    }

    @Operation(summary = "게스트 조회", description = "게스트 ID를 받아 해당 게스트 정보를 조회합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping("/{guestId}")
    public ApiResponseDto<Guest> getGuestById(@PathVariable Long guestId) {
        Guest guest = guestQueryService.findGuestById(guestId);
        return ApiResponseDto.onSuccess(guest);
    }

    @Operation(summary = "프로모션 ID로 게스트 조회", description = "프로모션 ID를 받아 해당 프로모션에 속한 모든 게스트 정보를 조회합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping("/promotion/{promotionId}")
    public ApiResponseDto<List<Guest>> getGuestsByPromotionId(@PathVariable Long promotionId) {
        List<Guest> guests = guestQueryService.findGuestsByPromotionId(promotionId);
        return ApiResponseDto.onSuccess(guests);
    }

    @Operation(summary = "프로모션 ID로 게스트 페이징 조회", description = "프로모션 ID를 받아 해당 프로모션에 속한 게스트 정보를 페이지별로 조회합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping("/promotion/{promotionId}/page")
    public ApiResponseDto<Page<Guest>> getGuestsByPromotionIdPaged(
            @PathVariable Long promotionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Guest> guestPage = guestQueryService.findGuestsByPromotionId(promotionId, pageable);
        return ApiResponseDto.onSuccess(guestPage);
    }
}