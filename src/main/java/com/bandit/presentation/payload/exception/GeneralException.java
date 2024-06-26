package com.bandit.presentation.payload.exception;

import com.bandit.presentation.payload.code.BaseCode;
import com.bandit.presentation.payload.code.Reason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private BaseCode code;

    @Override
    public String getMessage() {
        return code.getReason().getMessage();
    }

    public Reason getErrorReason() {
        return this.code.getReason();
    }

    public Reason getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
