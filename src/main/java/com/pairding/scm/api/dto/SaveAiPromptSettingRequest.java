package com.pairding.scm.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "AI 프롬프트 설정 저장 요청")
public record SaveAiPromptSettingRequest(

        @Schema(description = "System Prompt", example = "You are a senior code reviewer...")
        @NotBlank
        String systemPrompt,

        @Schema(description = "Review Prompt Template", example = "Review this diff and suggest improvements...")
        @NotBlank
        String reviewPromptTemplate,

        @Schema(description = "옵션: 코드 스타일 개선", example = "true")
        boolean codeStyleImprove,

        @Schema(description = "옵션: 잠재적 버그 수정", example = "true")
        boolean bugFix,

        @Schema(description = "옵션: 리팩토링 포함", example = "false")
        boolean refactoring,

        @Schema(description = "AI 리뷰 기능 활성화 여부", example = "true")
        boolean enabled
) {}
