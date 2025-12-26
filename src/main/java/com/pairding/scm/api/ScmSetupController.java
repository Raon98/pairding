package com.pairding.scm.api;
import com.pairding.global.api.ApiResponse;
import com.pairding.scm.api.dto.SaveAiPromptSettingRequest;
import com.pairding.scm.api.dto.SaveWebhookSettingRequest;
import com.pairding.scm.api.dto.SelectRepositoryRequest;
import com.pairding.scm.application.dto.RepositoryInfoResult;
import com.pairding.scm.application.dto.SelectRepositoryCommand;
import com.pairding.scm.application.dto.SelectRepositoryResult;
import com.pairding.scm.application.port.in.CompleteRepositorySetupUseCase;
import com.pairding.scm.application.port.in.GetRepositoriesUseCase;
import com.pairding.scm.application.port.in.SaveAiPromptSettingUseCase;
import com.pairding.scm.application.port.in.SaveWebhookSettingUseCase;
import com.pairding.scm.application.port.in.SelectRepositoryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "SCM Setup", description = "저장소 연동/설정 플로우 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scm")
public class ScmSetupController {
    private final GetRepositoriesUseCase getRepositoriesUseCase;
    private final SelectRepositoryUseCase selectRepositoryUseCase;
    private final SaveWebhookSettingUseCase saveWebhookSettingUseCase;
    private final SaveAiPromptSettingUseCase saveAiPromptSettingUseCase;
    private final CompleteRepositorySetupUseCase completeRepositorySetupUseCase;
    
    @Operation(summary = "SCM 저장소 목록 조회", description = "외부 SCM(GitHub/GitLab)에서 사용자 저장소 목록을 조회합니다.")
    @GetMapping("/{provider}/repositories")
    public ResponseEntity<ApiResponse<List<RepositoryInfoResult>>> getRepositories(
            @PathVariable String provider,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(getRepositoriesUseCase.execute(provider, userId)));
    }
    @Operation(summary = "저장소 선택", description = "선택한 저장소를 ConnectedRepository(DRAFT)로 생성하거나 기존 연결을 갱신합니다.")
    @PostMapping("/{provider}/repositories/select")
    public ResponseEntity<ApiResponse<SelectRepositoryResult>> selectRepository(
            @PathVariable String provider,
            @Valid @RequestBody SelectRepositoryRequest request
    ) {
        SelectRepositoryResult result = selectRepositoryUseCase.execute(
                new SelectRepositoryCommand(
                        request.userId(),
                        provider,
                        request.providerRepoId(),
                        request.owner(),
                        request.name(),
                        request.fullName(),
                        request.isPrivate(),
                        request.defaultBranch()
                )
        );
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
    @Operation(summary = "Webhook 설정 저장", description = "브랜치 필터/활성화 여부를 저장합니다.")
    @PutMapping("/connected-repositories/{connectedRepositoryId}/webhook-settings")
    public ResponseEntity<ApiResponse<Void>> saveWebhookSetting(
            @PathVariable Long connectedRepositoryId,
            @Valid @RequestBody SaveWebhookSettingRequest request
    ) {
        saveWebhookSettingUseCase.execute(
                connectedRepositoryId,
                request.branchFilter(),
                request.enabled()
        );
        return ResponseEntity.ok(ApiResponse.ok());
    }
    @Operation(summary = "AI 프롬프트 설정 저장", description = "AI 리뷰 프롬프트 및 옵션을 저장합니다.")
    @PutMapping("/connected-repositories/{connectedRepositoryId}/ai-prompt-settings")
    public ResponseEntity<ApiResponse<Void>> saveAiPromptSetting(
            @PathVariable Long connectedRepositoryId,
            @Valid @RequestBody SaveAiPromptSettingRequest request
    ) {
        saveAiPromptSettingUseCase.execute(
                connectedRepositoryId,
                request.systemPrompt(),
                request.reviewPromptTemplate(),
                request.codeStyleImprove(),
                request.bugFix(),
                request.refactoring(),
                request.enabled()
        );
        return ResponseEntity.ok(ApiResponse.ok());
    }
    @Operation(summary = "연동 완료", description = "외부 SCM에 Webhook을 실제 생성하고 ConnectedRepository를 ACTIVE로 전환합니다.")
    @PostMapping("/connected-repositories/{connectedRepositoryId}/complete")
    public ResponseEntity<ApiResponse<Void>> complete(
            @PathVariable Long connectedRepositoryId,
            @RequestHeader("X-Scm-Access-Token") String accessToken
    ) {
        completeRepositorySetupUseCase.execute(connectedRepositoryId, accessToken);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
