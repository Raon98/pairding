package com.pairding.scm.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Webhook 설정 저장 요청")
public record SaveWebhookSettingRequest(

        @Schema(description = "브랜치 필터 (예: main, develop)", example = "main")
        @NotBlank
        String branchFilter,

        @Schema(description = "Webhook 활성화 여부", example = "true")
        boolean enabled
) {}