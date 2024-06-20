package com.rockoon.presentation.payload.exception;

import com.rockoon.presentation.payload.code.BaseCode;

public class GuestHandler extends GeneralException{
    public GuestHandler(BaseCode code) {
        super(code);
    }
}
