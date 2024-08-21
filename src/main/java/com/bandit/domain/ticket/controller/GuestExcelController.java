package com.bandit.domain.ticket.controller;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.converter.GuestConverter;
import com.bandit.domain.ticket.dto.guest.GuestResponse;
import com.bandit.domain.ticket.dto.guest.GuestResponse.GuestListDto;
import com.bandit.domain.ticket.dto.guest.ReservationStatus;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.service.guest.GuestQueryService;
import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.global.annotation.auth.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.bandit.global.annotation.api.PredefinedErrorStatus.AUTH;

@Tag(name = "Guest API EXCEL DOWNLOAD", description = "게스트 데이터 엑셀 정리")
@ApiResponse(responseCode = "2000", description = "성공")
@RequiredArgsConstructor
@RequestMapping("/excel/guests")
@Controller
public class GuestExcelController {
    private final GuestQueryService guestQueryService;

    @Operation(summary = "예약자 명단 엑셀 파일 정리 🔑", description = "특정 프로모션의 예약자들을 모두 엑셀파일로 정리하여 다운로드합니다")
    @ApiErrorCodeExample(status = AUTH)
    @GetMapping("/{promotionId}/download")
    public void downloadReservation(@AuthUser Member member,
                                    @PathVariable Long promotionId,
                                    HttpServletResponse httpRes) throws Exception {
        List<Guest> guests = guestQueryService.findGuestsByPromotionId(promotionId, member);
        GuestListDto listDto = GuestConverter.toListDto(guests);

        // Excel Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("예약자 명단");

        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("예약자 이름");
        headerRow.createCell(1).setCellValue("예약 동반자 수");
        headerRow.createCell(2).setCellValue("예약 상태");

        // 데이터 작성
        int rowNum = 1;
        for (GuestResponse.GuestViewDto guestViewDto : listDto.getGuestList()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(guestViewDto.getName());
            row.createCell(1).setCellValue(guestViewDto.getReservationCount());
            row.createCell(2).setCellValue(getReservationStatusToKorean(guestViewDto.getReservationStatus()));
        }

        //파일 이름
        String fileName = "reservation_of_play_QR.xlsx";

        // HTTP 응답 헤더 설정
        httpRes.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpRes.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 엑셀 파일을 HTTP 응답으로 출력
        ServletOutputStream outputStream = httpRes.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private String getReservationStatusToKorean(ReservationStatus status) {
        switch (status) {
            case CHECKED_IN -> {
                return "입장완료";
            }
            case AFTER_CONFIRMATION -> {
                return "예매승인";
            }
            case BEFORE_CONFIRMATION -> {
                return "예매승인대기중";
            }
            default -> {
                return "예외";
            }
        }
    }
}
