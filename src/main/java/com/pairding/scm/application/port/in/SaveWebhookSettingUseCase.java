package com.pairding.scm.application.port.in;

import org.springframework.stereotype.Service;

import com.pairding.scm.domain.model.RepoWebhookSetting;
import com.pairding.scm.domain.repository.RepoWebhookSettingStore;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveWebhookSettingUseCase {
    private final RepoWebhookSettingStore store;

    @Transactional
    public void execute(Long connectedRepositoryId,
                        String branchFilter,
                        Boolean enabled) {
            store.findByConnectedRepositoryId(connectedRepositoryId)
                            .ifPresentOrElse(
                                setting -> setting.update(branchFilter, enabled),
                                () -> store.save(
                                                RepoWebhookSetting.create(connectedRepositoryId,branchFilter)
                                ));
            
    }
}
