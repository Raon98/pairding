package com.pairding.scm.domain.repository;

import com.pairding.scm.domain.enums.ScmProvider;
import com.pairding.scm.domain.model.ConnectedRepository;

import java.util.Optional;

public interface ConnectedRepositoryStore  {
    Optional<ConnectedRepository> findByUserIdAndProviderAndProviderRepoId(Long userId, ScmProvider provider, String providerRepoId);
    ConnectedRepository save(ConnectedRepository repo);
}
