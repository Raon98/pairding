package com.pairding.scm.infrastructure.db.store;

import java.util.Optional;

import com.pairding.scm.domain.model.RepoAiPromptSetting;
import com.pairding.scm.domain.repository.RepoAiPromptSettingStore;
import com.pairding.scm.infrastructure.db.repository.RepoAiPromptSettingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepoAiPromptSettingStoreImpl implements RepoAiPromptSettingStore {

    private final RepoAiPromptSettingJpaRepository repository;

    @Override
    public Optional<RepoAiPromptSetting> findByConnectedRepositoryId(Long connectedRepositoryId) {
        return repository.findByConnectedRepositoryId(connectedRepositoryId);
    }

    @Override
    public RepoAiPromptSetting save(RepoAiPromptSetting setting) {
        return repository.save(setting);
    }

    @Override
    public boolean existsByConnectedRepositoryId(Long connectedRepositoryId) {
        return repository.existsByConnectedRepositoryId(connectedRepositoryId);
    }
}