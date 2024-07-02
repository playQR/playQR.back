package com.bandit.presentation.payload.exception;


import com.bandit.presentation.payload.code.BaseCode;

public class TicketHandler extends GeneralException{
    public TicketHandler(BaseCode code) {
        super(code);
    }
}
