package com.pairding.scm.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "SCM 저장소 정보 결과")
public record RepositoryInfoResult(

        @Schema(description = "Provider 내 저장소 ID", example = "789365156101822874")
        String providerRepoId,

        @Schema(description = "소유자/조직(owner or namespace)", example = "oct")
        String owner,

        @Schema(description = "저장소 이름", example = "my-project")
        String name,

        @Schema(description = "전체 이름 (owner/repo)", example = "sungcheol1998/my-project")
        String fullName,

        @Schema(description = "비공개 여부", example = "false")
        boolean isPrivate
) {}
