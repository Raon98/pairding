package com.pairding.scm.application.port.in;

import com.pairding.scm.application.dto.RepositorySetupNextStep;
import com.pairding.scm.application.dto.SelectRepositoryCommand;
import com.pairding.scm.application.dto.SelectRepositoryResult;
import com.pairding.scm.domain.enums.ConnectedRepoStatus;
import com.pairding.scm.domain.enums.ScmProvider;
import com.pairding.scm.domain.model.ConnectedRepository;
import com.pairding.scm.domain.repository.ConnectedRepositoryStore;
import com.pairding.scm.domain.repository.RepoAiPromptSettingStore;
import com.pairding.scm.domain.repository.RepoWebhookSettingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class SelectRepositoryUseCase {
    private final ConnectedRepositoryStore connectedRepositoryStore;
    private final RepoWebhookSettingStore webhookSettingStore;
    private final RepoAiPromptSettingStore aiPromptSettingStore;
    
    @Transactional
    public SelectRepositoryResult execute(SelectRepositoryCommand cmd) {
        ScmProvider provider = ScmProvider.from(cmd.provider());
        return connectedRepositoryStore
            .findByUserIdAndProviderAndProviderRepoId(cmd.userId(), provider, cmd.providerRepoId())
            .map(existing -> {
                existing.updateDisplayInfo(
                    cmd.owner(),
                    cmd.name(),
                    cmd.fullName(),
                    cmd.isPrivate(),
                    cmd.defaultBranch()
                );
                RepositorySetupNextStep nextStep = computeNextStep(existing.getId(), existing.getStatus());
                return new SelectRepositoryResult(existing.getId(), existing.getStatus(), nextStep);
            })
            .orElseGet(() -> {
                ConnectedRepository draft = ConnectedRepository.draft(
                    cmd.userId(),
                    provider,
                    cmd.providerRepoId(),
                    cmd.owner(),
                    cmd.name(),
                    cmd.fullName(),
                    cmd.isPrivate(),
                    cmd.defaultBranch()
                );
                ConnectedRepository saved = connectedRepositoryStore.save(draft);
                return new SelectRepositoryResult(saved.getId(), saved.getStatus(), RepositorySetupNextStep.WEBHOOK);
            });
    }

    private RepositorySetupNextStep computeNextStep(Long connectedRepositoryId, ConnectedRepoStatus status) {
        if (status == ConnectedRepoStatus.ACTIVE) return RepositorySetupNextStep.COMPLETE;
        if (!webhookSettingStore.existsByConnectedRepositoryId(connectedRepositoryId)) {
            return RepositorySetupNextStep.WEBHOOK;
        }
        if (!aiPromptSettingStore.existsByConnectedRepositoryId(connectedRepositoryId)) {
            return RepositorySetupNextStep.AI_PROMPT;
        }
        return RepositorySetupNextStep.COMPLETE;
    }
}