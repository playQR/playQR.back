package com.bandit.presentation.payload.exception;

import com.bandit.presentation.payload.code.BaseCode;

public class KakaoHandler extends GeneralException{
    public KakaoHandler(BaseCode code) {
        super(code);
    }
}
