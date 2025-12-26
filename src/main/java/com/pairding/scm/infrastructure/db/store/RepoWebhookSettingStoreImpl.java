package com.pairding.scm.infrastructure.db.store;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pairding.scm.domain.model.RepoWebhookSetting;
import com.pairding.scm.domain.repository.RepoWebhookSettingStore;
import com.pairding.scm.infrastructure.db.repository.RepoWebhookSettingJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepoWebhookSettingStoreImpl implements RepoWebhookSettingStore{
    
    private final RepoWebhookSettingJpaRepository repository;

    @Override
    public Optional<RepoWebhookSetting> findByConnectedRepositoryId(Long connectedRepositoryId) {
        return repository.findByConnectedRepositoryId(connectedRepositoryId);
    }

    @Override
    public RepoWebhookSetting save(RepoWebhookSetting setting) {
        return repository.save(setting);
    }

    @Override
    public boolean existsByConnectedRepositoryId(Long connectedRepositoryId) {
        return repository.existsByConnectedRepositoryId(connectedRepositoryId);
    }
    
}
