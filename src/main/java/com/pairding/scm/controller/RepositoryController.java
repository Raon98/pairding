package com.pairding.scm.controller;

import com.pairding.scm.controller.dto.RepositoryInfoResponse;
import com.pairding.scm.service.RepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scm")
@RequiredArgsConstructor
@Tag(name = "사용자 저장소 API", description = "GitHub/GitLab 저장소 조회 API")
public class RepositoryController {

    private final RepositoryService repositoryService;

    @Operation(
            summary = "사용자 저장소 목록 조회",
            description = "GitHub 또는 GitLab 에서 사용자 저장소 목록을 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "저장소 목록 조회 성공")
    @GetMapping("/{provider}/repos")
    public List<RepositoryInfoResponse> getRepos(
            @Parameter(description = "SCM 제공자 (github / gitlab)", example = "github")
            @PathVariable String provider,

            @Parameter(description = "사용자 ID(TSID)", example = "789365156101822874")
            @RequestParam Long userId
    ) {
        return repositoryService.getRepositories(provider, userId)
                .stream()
                .map(RepositoryInfoResponse::from)
                .toList();
    }
}
