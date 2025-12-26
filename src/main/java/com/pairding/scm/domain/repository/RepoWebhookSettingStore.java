package com.pairding.scm.domain.repository;

import java.util.Optional;

import com.pairding.scm.domain.model.RepoWebhookSetting;

public interface RepoWebhookSettingStore {
    Optional<RepoWebhookSetting> findByConnectedRepositoryId(Long connectedRepositoryId);  

    RepoWebhookSetting save(RepoWebhookSetting setting);

    boolean existsByConnectedRepositoryId(Long connectedRepositoryId);
}
