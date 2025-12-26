package com.pairding.scm.infrastructure.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pairding.scm.domain.model.RepoAiPromptSetting;
import com.pairding.scm.domain.model.RepoWebhookSetting;

public interface RepoAiPromptSettingJpaRepository extends JpaRepository<RepoWebhookSetting,Long>{
    boolean existsByConnectedRepositoryId(Long connectedRepositoryId);

    Optional<RepoAiPromptSetting> findByConnectedRepositoryId(Long connectedRepositoryId);

    RepoAiPromptSetting save(RepoAiPromptSetting setting);

} 