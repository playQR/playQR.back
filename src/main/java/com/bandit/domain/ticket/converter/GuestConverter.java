package com.bandit.domain.ticket.converter;

import com.bandit.domain.member.converter.MemberConverter;
import com.bandit.domain.ticket.dto.guest.GuestResponse.GuestListDto;
import com.bandit.domain.ticket.dto.guest.GuestResponse.GuestViewDto;
import com.bandit.domain.ticket.entity.Guest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static com.bandit.domain.ticket.dto.guest.ReservationStatus.getStatus;


public class GuestConverter {
    public static GuestViewDto toViewDto(Guest guest) {
        return GuestViewDto.builder()
                .guestId(guest.getId())
                .name(guest.getName())
                .depositDate(guest.getDepositDate())
                .reservationStatus(getStatus(guest))
                .reservationCount(guest.getReservationCount())
                .writer(MemberConverter.toResponse(guest.getMember()))
                .build();
    }
    public static GuestListDto toListDto(List<Guest> guestList) {
        List<GuestViewDto> collect = guestList.stream()
                .map(GuestConverter::toViewDto)
                .collect(Collectors.toList());
        return GuestListDto.builder()
                .guestList(collect)
                .build();
    }
    public static GuestListDto toListDto(Page<Guest> guestPage) {
        List<GuestViewDto> collect = guestPage.getContent().stream()
                .map(GuestConverter::toViewDto)
                .collect(Collectors.toList());
        return GuestListDto.builder()
                .guestList(collect)
                .build();
    }
}
