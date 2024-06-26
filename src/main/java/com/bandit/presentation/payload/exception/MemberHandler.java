package com.bandit.presentation.payload.exception;

import com.bandit.presentation.payload.code.BaseCode;

public class MemberHandler extends GeneralException{
    public MemberHandler(BaseCode code) {
        super(code);
    }
}
