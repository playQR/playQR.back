package com.rockoon.presentation.payload.exception;

import com.rockoon.presentation.payload.code.BaseCode;

public class MemberHandler extends GeneralException{
    public MemberHandler(BaseCode code) {
        super(code);
    }
}
