package com.pairding.scm.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "저장소 선택 요청")
public record SelectRepositoryRequest(

        @Schema(description = "서비스 사용자 ID(TSID)", example = "1000000000000")
        @NotNull
        Long userId,

        @Schema(description = "Provider 내 저장소 ID", example = "654321987")
        @NotNull
        String providerRepoId,

        @Schema(description = "저장소 소유자", example = "sungcheol")
        @NotBlank
        String owner,

        @Schema(description = "저장소 이름", example = "pairding-backend")
        @NotBlank
        String name,

        @Schema(description = "전체 이름(owner/name)", example = "sungcheol/pairding-backend")
        @NotBlank
        String fullName,

        @Schema(description = "비공개 저장소 여부", example = "true")
        boolean isPrivate,

        @Schema(description = "기본 브랜치", example = "main")
        String defaultBranch
) {}