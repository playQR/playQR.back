package com.bandit.domain.showOption.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    TIME("TIME"), LOCATION("LOCATION"), FEE("FEE"), ETC("ETC");

    private final String title;
}
