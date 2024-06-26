package com.bandit.global.annotation.auth;


import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.service.MemberQueryService;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.SecurityHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

@Slf4j
@RequiredArgsConstructor
public final class CustomAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberQueryService memberQueryService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return findMethodAnnotation(AuthUser.class, parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        AuthUser annotation = findMethodAnnotation(AuthUser.class, parameter);
        if (principal == "anonymousUser") {
            throw new SecurityHandler(ErrorStatus._UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR);
        }
        findMethodAnnotation(AuthUser.class, parameter);
        Member member = memberQueryService.getMemberByUsername(authentication.getName());

        if (principal != null && !ClassUtils.isAssignable(parameter.getParameterType(), member.getClass())) {
            if (annotation.errorOnInvalidType()) {
                throw new SecurityHandler(ErrorStatus._ASSIGNABLE_PARAMETER);
            }
        }

        return member;
    }

    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }
}