package com.bandit.domain.ticket.controller;

import com.bandit.domain.board.converter.PromotionConverter;
import com.bandit.domain.board.dto.promotion.PromotionResponse.GuestPromotionSummaryDto;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.service.promotion.PromotionQueryService;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.converter.GuestConverter;
import com.bandit.domain.ticket.dto.guest.GuestRequest;
import com.bandit.domain.ticket.dto.guest.GuestResponse;
import com.bandit.domain.ticket.dto.guest.GuestResponse.GuestListDto;
import com.bandit.domain.ticket.dto.guest.GuestResponse.PromotionReservationDto;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.service.guest.GuestCommandService;
import com.bandit.domain.ticket.service.guest.GuestQueryService;
import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.global.annotation.auth.AuthUser;
import com.bandit.global.util.PageUtil;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.bandit.global.annotation.api.PredefinedErrorStatus.AUTH;

@Tag(name = "Guest API", description = "게스트 관련 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestApiController {

    private final GuestCommandService guestCommandService;
    private final GuestQueryService guestQueryService;
    private final PromotionQueryService promotionQueryService;

    @Operation(summary = "게스트 생성 🔑", description = "프로모션 ID와 멤버 정보, 이름을 받아 새로운 게스트를 생성합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.PROMOTION_NOT_FOUND
    }, status = AUTH)
    @PostMapping("/{promotionId}")
    public ApiResponseDto<Long> createGuest(
            @PathVariable Long promotionId,
            @RequestBody GuestRequest request,
            @AuthUser Member member) {
        Long guestId = guestCommandService.createGuest(promotionId, member, request);
        return ApiResponseDto.onSuccess(guestId);
    }

    @Operation(summary = "게스트 입장 🔑", description = "프로모션의 티켓 uuid를 통해 게스트를 입장 처리해줍니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.TICKET_NOT_FOUND,
            ErrorStatus.GUEST_NOT_FOUND
    }, status = AUTH)
    @PostMapping("/entrance")
    public ApiResponseDto<Boolean> entranceGuest(@RequestParam String uuid,
                                                 @AuthUser Member member) {
        guestCommandService.entrance(uuid, member);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "게스트 수정 🔑", description = "게스트 ID와 게스트 정보를 받아 기존 게스트 정보를 수정합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.GUEST_NOT_FOUND,
            ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR
    }, status = AUTH)
    @PutMapping("/{guestId}")       //TODO remove this api(not use)
    public ApiResponseDto<Long> updateGuest(
            @PathVariable Long guestId,
            @AuthUser Member member,
            @RequestBody @Valid GuestRequest request) {
        return ApiResponseDto.onSuccess(guestCommandService.updateGuest(guestId, member, request));
    }

    @Operation(summary = "게스트 삭제 🔑", description = "게스트 ID를 받아 해당 게스트를 삭제합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.GUEST_NOT_FOUND,
            ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR
    }, status = AUTH)
    @DeleteMapping("/{guestId}")
    public ApiResponseDto<Boolean> deleteGuest(@PathVariable Long guestId,
                                               @AuthUser Member member) {
        guestCommandService.deleteGuest(guestId, member);
        return ApiResponseDto.onSuccess(true);
    }


    @Operation(summary = "게스트 조회 🔑", description = "게스트 ID를 받아 해당 게스트 정보를 조회합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.GUEST_NOT_FOUND,
            ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR
    }, status = AUTH)
    @GetMapping("/{guestId}")
    public ApiResponseDto<GuestResponse.GuestViewDto> getGuestById(@PathVariable Long guestId,
                                                                   @AuthUser Member member) {
        return ApiResponseDto.onSuccess(GuestConverter.toViewDto(guestQueryService.findGuestById(guestId, member)));
    }

    @Operation(summary = "프로모션 ID로 게스트 조회 🔑", description = "프로모션 ID를 받아 해당 프로모션에 속한 모든 게스트 정보를 조회합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.PROMOTION_NOT_FOUND,
            ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR
    }, status = AUTH)
    @GetMapping("/promotions/{promotionId}")
    public ApiResponseDto<GuestListDto> getGuestsByPromotionId(@PathVariable Long promotionId,
                                                               @AuthUser Member member) {
        List<Guest> guests = guestQueryService.findGuestsByPromotionId(promotionId, member);
        return ApiResponseDto.onSuccess(GuestConverter.toListDto(guests));
    }

    @Operation(summary = "프로모션 ID로 게스트 페이징 조회 🔑", description = "프로모션 ID를 받아 해당 프로모션에 속한 게스트 정보를 페이지별로 조회합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.PROMOTION_NOT_FOUND,
            ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR
    }, status = AUTH)
    @GetMapping("/promotions/{promotionId}/page")
    public ApiResponseDto<GuestListDto> getGuestsByPromotionIdPaged(
            @PathVariable Long promotionId,
            @AuthUser Member member,
            @RequestParam(defaultValue = "0") int currentPage) {
        PageRequest pageable = PageRequest.of(currentPage, PageUtil.GUEST_SIZE);
        Page<Guest> guestPage = guestQueryService.findGuestsByPromotionId(promotionId, member, pageable);
        return ApiResponseDto.onSuccess(GuestConverter.toListDto(guestPage));
    }

    @Operation(summary = "게스트의 프로모션 페이징 조회 🔑", description = "로그인한 유저가 게스트로서 자신이 예매한 프로모션을 조회합니다.")
    @ApiErrorCodeExample(status = AUTH)
    @GetMapping("/guest/page")
    public ApiResponseDto<List<GuestPromotionSummaryDto>> getPromotionsAsGuest(
            @AuthUser Member member,
            @RequestParam(defaultValue = "0") int currentPage) {
        //TODO QUERYDSL로 최적화하기
        PageRequest pageable = PageRequest.of(currentPage, PageUtil.PROMOTION_SIZE);
        Page<Promotion> promotionPage = promotionQueryService.getPaginationPromotionAsGuest(member, pageable);
        List<GuestPromotionSummaryDto> responseDtoList = new ArrayList<>();
        promotionPage.getContent()
                .forEach(promotion -> {
                    Guest guest = guestQueryService.findByPromotionAndMember(promotion, member);
                    responseDtoList.add(PromotionConverter.toGuestPromotionSummaryDto(promotion, guest));
                });
        return ApiResponseDto.onSuccess(responseDtoList);
    }

    @Operation(summary = "게스트 예매 승인 🔑", description = "로그인 한 호스트가 게스트의 예매 승인 처리를 합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.GUEST_NOT_FOUND,
            ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR
    }, status = AUTH)
    @PostMapping("/{guestId}/reservation/confirmation")
    public ApiResponseDto<Boolean> confirmationReservation(@AuthUser Member member,
                                                           @PathVariable Long guestId) {
        guestCommandService.approve(guestId, member);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "프로모션 예약 현황조회", description = "프로모션의 예매 현황을 알려줍니다.")
    @ApiErrorCodeExample({ErrorStatus.PROMOTION_NOT_FOUND})
    @GetMapping("/{promotionId}/reservation/count")
    public ApiResponseDto<PromotionReservationDto> getReservationInfoAsPromotion(@PathVariable Long promotionId) {
        return ApiResponseDto.onSuccess(guestQueryService.getReservationInfo(promotionId));
    }
}