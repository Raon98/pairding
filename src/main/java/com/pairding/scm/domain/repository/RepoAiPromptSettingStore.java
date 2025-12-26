package com.pairding.scm.domain.repository;

import java.util.Optional;

import com.pairding.scm.domain.model.RepoAiPromptSetting;

public interface RepoAiPromptSettingStore {
    Optional<RepoAiPromptSetting> findByConnectedRepositoryId(Long connectedRepositoryId);
    RepoAiPromptSetting save(RepoAiPromptSetting setting);
    boolean existsByConnectedRepositoryId(Long connectedRepositoryId);
}
