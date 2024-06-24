package com.rockoon.global.util;

import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.MemberHandler;

import java.util.UUID;

public class RandomNameUtil {

    private static final String BANDIT_AUTO_NAME = "이름";
    private static final String BANDIT_AUTO_NICKNAME = "nickname";

    public enum NameType {
        USERNAME, NICKNAME, NAME
    }

    public static String generateAuto(NameType nameType) {
        String generatedName;
        switch (nameType) {
            case NAME -> {
                generatedName = String.valueOf(getStringBuilder(BANDIT_AUTO_NAME));
            }
            case NICKNAME -> {
                generatedName = String.valueOf(getStringBuilder(BANDIT_AUTO_NICKNAME));
            }
            case USERNAME -> {
                generatedName = UUID.randomUUID().toString();
            }
            default -> {
                throw new MemberHandler(ErrorStatus.MEMBER_NAME_TYPE_IS_INVALID);
            }
        }
        return generatedName;
    }
    private static StringBuilder getStringBuilder(String prefix) {
        String uniqueValue = UUID.randomUUID().toString().substring(0, 5).replace("-", "#");

        StringBuilder usernameBuilder = new StringBuilder();
        usernameBuilder.append(prefix).append(uniqueValue);
        return usernameBuilder;
    }
}
