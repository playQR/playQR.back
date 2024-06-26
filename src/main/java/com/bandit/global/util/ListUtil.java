package com.bandit.global.util;

import java.util.List;

public class ListUtil {
    public static boolean isNullOrEmpty(List<?> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }
}
