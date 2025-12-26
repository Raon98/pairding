package com.pairding.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 공통
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."),

    // SCM
    REPOSITORY_NOT_FOUND(HttpStatus.NOT_FOUND, "저장소를 찾을 수 없습니다."),
    WEBHOOK_NOT_CONFIGURED(HttpStatus.BAD_REQUEST, "Webhook 설정이 완료되지 않았습니다."),
    AI_SETTING_NOT_CONFIGURED(HttpStatus.BAD_REQUEST, "AI 설정이 완료되지 않았습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus status() {
        return status;
    }

    public String message() {
        return message;
    }
}