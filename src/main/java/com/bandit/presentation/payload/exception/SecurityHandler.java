package com.bandit.presentation.payload.exception;

import com.bandit.presentation.payload.code.BaseCode;

public class SecurityHandler extends GeneralException{
    public SecurityHandler(BaseCode code) {
        super(code);
    }
}
