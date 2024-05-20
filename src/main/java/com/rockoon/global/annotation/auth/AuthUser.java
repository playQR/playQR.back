package com.rockoon.global.annotation.auth;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@Parameter(hidden=true)      TODO swagger 사용 시 설정 해주기
@Documented
public @interface AuthUser {
    boolean errorOnInvalidType() default true;
}
