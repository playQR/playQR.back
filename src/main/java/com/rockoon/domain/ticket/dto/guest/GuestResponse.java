package com.rockoon.domain.ticket.dto.guest;

import com.rockoon.domain.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestDetailDto {
        private Long guestId;
        private String guestname;
        private MemberResponse writer;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestSummaryDto {
        private Long guestId;
        private String guestname;
        private MemberResponse writer;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestListDto {
        private List<GuestSummaryDto> guestList;
    }
}
