package com.bandit.domain.manager.converter;

import com.bandit.domain.member.converter.MemberConverter;
import com.bandit.domain.manager.dto.ManagerResponse.ManagerListDto;
import com.bandit.domain.manager.dto.ManagerResponse.ManagerViewDto;
import com.bandit.domain.manager.entity.Manager;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ManagerConverter {

    public static ManagerViewDto toViewDto(Manager manager) {
        return ManagerViewDto.builder()
                .managerId(manager.getId())
                .member(MemberConverter.toResponse(manager.getMember()))
                .build();
    }

    public static ManagerListDto toListDto(List<Manager> managerList) {
        List<ManagerViewDto> collect = managerList.stream()
                .map(ManagerConverter::toViewDto)
                .collect(Collectors.toList());
        return ManagerListDto.builder()
                .managerList(collect)
                .build();
    }

    public static ManagerListDto toListDto(Page<Manager> managerPage) {
        List<ManagerViewDto> collect = managerPage.getContent().stream()
                .map(ManagerConverter::toViewDto)
                .collect(Collectors.toList());
        return ManagerListDto.builder()
                .managerList(collect)
                .build();
    }
}
