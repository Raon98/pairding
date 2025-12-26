package com.pairding.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공통 에러 응답")
public record ApiErrorResponse(
        boolean success,
        String errorCode,
        String message
) {
    public static ApiErrorResponse from(ErrorCode errorCode) {
        return new ApiErrorResponse(
                false,
                errorCode.name(),
                errorCode.message()
        );
    }
}
