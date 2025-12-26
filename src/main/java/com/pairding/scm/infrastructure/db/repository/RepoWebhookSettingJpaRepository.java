package com.pairding.scm.infrastructure.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pairding.scm.domain.model.RepoWebhookSetting;

public interface RepoWebhookSettingJpaRepository extends JpaRepository<RepoWebhookSetting,Long>{
    boolean existsByConnectedRepositoryId(Long connectedRepositoryId);

    Optional<RepoWebhookSetting> findByConnectedRepositoryId(Long connectedRepositoryId);

} 