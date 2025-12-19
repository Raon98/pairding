package com.pairding.scm.domain.repository;

import com.pairding.scm.domain.model.ConnectedRepository;

import java.util.Optional;

public interface ConnectedRepoStore  {
    Optional<ConnectedRepository> findByUserIdAndProviderAndFullName(Long userId, String provider, String fullName);
    ConnectedRepository save(ConnectedRepository repo);
}
