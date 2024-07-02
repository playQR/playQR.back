package com.bandit.presentation.payload.exception;

import com.bandit.presentation.payload.code.BaseCode;

public class GuestHandler extends GeneralException{
    public GuestHandler(BaseCode code) {
        super(code);
    }
}
