package com.pairding.scm.application.port.in;

import com.pairding.scm.domain.model.ConnectedRepository;
import com.pairding.scm.domain.repository.ConnectedRepositoryStore;
import com.pairding.scm.domain.repository.RepoAiPromptSettingStore;
import com.pairding.scm.domain.repository.RepoWebhookSettingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompleteRepositorySetupUseCase {

    private final ConnectedRepositoryStore connectedRepositoryStore;
    private final RepoWebhookSettingStore webhookSettingStore;
    private final RepoAiPromptSettingStore aiPromptSettingStore;

    @Transactional
    public void execute(Long connectedRepositoryId) {

        ConnectedRepository repo = connectedRepositoryStore.findById(connectedRepositoryId)
            .orElseThrow(() -> new IllegalStateException("연결된 저장소가 없습니다."));

        if (!webhookSettingStore.existsByConnectedRepositoryId(connectedRepositoryId)) {
            throw new IllegalStateException("Webhook 설정이 완료되지 않았습니다.");
        }

        if (!aiPromptSettingStore.existsByConnectedRepositoryId(connectedRepositoryId)) {
            throw new IllegalStateException("AI 프롬프트 설정이 완료되지 않았습니다.");
        }

        repo.activate();
        connectedRepositoryStore.save(repo);
    }
}
