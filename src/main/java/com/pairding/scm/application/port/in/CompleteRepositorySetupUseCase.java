package com.pairding.scm.application.port.in;

import com.pairding.global.core.tsid.TsidGenerator;
import com.pairding.scm.application.port.out.SourceControlPort;
import com.pairding.scm.application.resolver.SourceControlServiceResolver;
import com.pairding.scm.domain.model.ConnectedRepository;
import com.pairding.scm.domain.model.RepoWebhookSetting;
import com.pairding.scm.domain.repository.ConnectedRepositoryStore;
import com.pairding.scm.domain.repository.RepoAiPromptSettingStore;
import com.pairding.scm.domain.repository.RepoWebhookSettingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompleteRepositorySetupUseCase {
    private final ConnectedRepositoryStore connectedRepositoryStore;
    private final RepoWebhookSettingStore webhookSettingStore;
    private final RepoAiPromptSettingStore aiPromptSettingStore;
    private final SourceControlServiceResolver resolver;

    @Value("${scm.webhook.callback-url}")
    private String webhookCallbackUrl;
    @Transactional
    public void execute(Long connectedRepositoryId, String accessToken) {
        ConnectedRepository repository =
                connectedRepositoryStore.findById(connectedRepositoryId)
                        .orElseThrow(() -> new IllegalStateException("연결된 저장소가 없습니다."));
        RepoWebhookSetting webhookSetting =
                webhookSettingStore.findByConnectedRepositoryId(connectedRepositoryId)
                        .orElseThrow(() -> new IllegalStateException("Webhook 설정이 완료되지 않았습니다."));
        if (!aiPromptSettingStore.existsByConnectedRepositoryId(connectedRepositoryId)) {
            throw new IllegalStateException("AI 프롬프트 설정이 완료되지 않았습니다.");
        }
       
        if (!webhookSetting.isWebhookCreated()) {
            String secret = TsidGenerator.nextString();
            SourceControlPort scm = resolver.resolve(repository.getProvider());
            SourceControlPort.CreateWebhookResult result =
                    scm.createPushWebhook(
                            new SourceControlPort.CreateWebhookCommand(
                                    accessToken,
                                    repository.getOwner(),
                                    repository.getName(),
                                    webhookCallbackUrl,
                                    secret
                            )
                    );
            webhookSetting.markWebhookCreated(result.providerWebhookId(), secret);
            webhookSettingStore.save(webhookSetting);
        }
        repository.activate();
        connectedRepositoryStore.save(repository);
    }
}
