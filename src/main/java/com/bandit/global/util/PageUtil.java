package com.bandit.global.util;

import org.springframework.data.domain.Page;

public class PageUtil {
    public static final int COMMENT_SIZE = 5;

    public static final int PROMOTION_SIZE = 10;
    public static final int GUEST_SIZE = 10;

    public static <T> int getNextPageParam(Page<T> pagination) {
        return pagination.getNumber() + 1;
    }
}
