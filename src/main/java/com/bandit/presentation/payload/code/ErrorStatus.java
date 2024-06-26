package com.bandit.presentation.payload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode{
    // 서버 오류
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, 5000, "서버 에러, 관리자에게 문의 바랍니다."),
    _UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR(INTERNAL_SERVER_ERROR, 5001, "서버 에러, 로그인이 필요없는 요청입니다."),
    _ASSIGNABLE_PARAMETER(BAD_REQUEST, 5002, "인증타입이 잘못되어 할당이 불가능합니다."),

    // 일반적인 요청 오류
    _BAD_REQUEST(BAD_REQUEST, 4000, "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, 4001, "로그인이 필요합니다."),
    _FORBIDDEN(FORBIDDEN, 4002, "금지된 요청입니다."),

    //인증 관련 오류(4050 ~ 4099)
    AUTH_INVALID_TOKEN(BAD_REQUEST, 4050, "유효하지 않은 토큰입니다."),
    AUTH_INVALID_REFRESH_TOKEN(BAD_REQUEST,4051, "유효하지 않은 리프레시토큰입니다"),
    AUTH_TOKEN_HAS_EXPIRED(BAD_REQUEST, 4053, "토큰의 유효기간이 만료되었습니다."),
    AUTH_TOKEN_IS_UNSUPPORTED(BAD_REQUEST,4054, "서버에서 지원하는 형식의 토큰이 아닙니다."),
    AUTH_IS_NULL(BAD_REQUEST,4055, "토큰 값이 존재하지 않습니다.(null)"),
    AUTH_OAUTH2_EMAIL_NOT_FOUND_FROM_PROVIDER(NOT_FOUND, 4056, "해당 이메일은 카카오 계정 이메일로 존재하지 않습니다."),
    AUTH_MUST_AUTHORIZED_URI(BAD_REQUEST, 4057, "인증이 필수인 uri입니다."),
    AUTH_ROLE_CANNOT_EXECUTE_URI(BAD_REQUEST,4058, "해당 인가로는 실행할 수 없는 동작입니다."),
    //회원 관련 오류(4100 ~ 4149)
    MEMBER_NOT_FOUND(NOT_FOUND, 4100, "존재하지 않는 회원입니다."),
    MEMBER_NAME_TYPE_IS_INVALID(NOT_FOUND, 4101, "존재하지 않는 이름타입입니다. 이름타입은 유저네임, 네임, 닉네임입니다."),

    //프로모션 관련 오류(4150 ~ 4199)
    PROMOTION_NOT_FOUND(NOT_FOUND, 4150, "존재하지 않는 프로모션입니다."),
    PROMOTION_ONLY_CAN_BE_TOUCHED_BY_WRITER(BAD_REQUEST, 4151, "작성자가 아닌 유저는 프로모션을 수정이 불가합니다."),

    //댓글 관련 오류(4200 ~ 4249)
    COMMENT_NOT_FOUND(NOT_FOUND, 4200, "존재하지 않는 댓글입니다"),
    COMMENT_CAN_BE_ONLY_TOUCHED_BY_WRITER(BAD_REQUEST, 4201,"댓글은 댓글작성자만 관리할 수 있습니다"),


    //이미지 관련 오류(4250 ~ 4299)
    IMAGE_REQUEST_IS_EMPTY(BAD_REQUEST, 4250, "요청한 이미지가 존재하지 않습니다"),

    //셑리스트 좋아요 관련 오류(4300 ~ 4349)
    LIKE_NOT_FOUND(NOT_FOUND, 4300, "존재하지 않는 좋아요 정보입니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;


    @Override
    public Reason getReason() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public Reason getReasonHttpStatus() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
