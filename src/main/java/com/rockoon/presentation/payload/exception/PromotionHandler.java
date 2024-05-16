package com.rockoon.presentation.payload.exception;

import com.rockoon.presentation.payload.code.BaseCode;

public class PromotionHandler extends GeneralException{
    public PromotionHandler(BaseCode code) {
        super(code);
    }
}
