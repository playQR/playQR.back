package com.rockoon.presentation.payload.exception;

import com.rockoon.presentation.payload.code.BaseCode;

public class LikeHandler extends GeneralException{
    public LikeHandler(BaseCode code) {
        super(code);
    }
}
