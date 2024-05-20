package com.rockoon.presentation.payload.exception;

import com.rockoon.presentation.payload.code.BaseCode;

public class SecurityHandler extends GeneralException{
    public SecurityHandler(BaseCode code) {
        super(code);
    }
}
