package com.pairding.scm.api.dto;

import com.pairding.scm.application.dto.RepositoryInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "저장소 정보 응답 DTO")
public class RepositoryInfoResponse {

    @Schema(description = "저장소 고유 ID", example = "789365156101822874")
    private Long id;

    @Schema(description = "소유자/조직(owner or namespace)", example = "oct")
    private String owner;

    @Schema(description = "저장소 이름", example = "my-project")
    private String name;

    @Schema(description = "전체이름 (owner/repo)", example = "sungcheol1998/my-project")
    private String fullName;

    @Schema(description = "비공개 여부", example = "false")
    private boolean isPrivate;

    public static RepositoryInfoResponse from(RepositoryInfo info) {
        return new RepositoryInfoResponse(
                info.getId(),
                info.getOwner(),
                info.getName(),
                info.getFullName(),
                info.isPrivate()
        );
    }
}
