package com.bandit.global.annotation.api;

import com.bandit.presentation.payload.code.ErrorStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.bandit.global.annotation.api.PredefinedErrorStatus.DEFAULT;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCodeExample {
    ErrorStatus[] value() default {ErrorStatus._INTERNAL_SERVER_ERROR};

    PredefinedErrorStatus status() default DEFAULT;
}
