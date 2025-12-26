package com.pairding.scm.infrastructure.db.store;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pairding.scm.domain.enums.ScmProvider;
import com.pairding.scm.domain.model.ConnectedRepository;
import com.pairding.scm.domain.repository.ConnectedRepositoryStore;
import com.pairding.scm.infrastructure.db.repository.ConnectedRepositoryJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConnectedRepositoryStoreImpl implements ConnectedRepositoryStore {

    private final ConnectedRepositoryJpaRepository repository;

    @Override
    public ConnectedRepository save(ConnectedRepository repo) {
        return repository.save(repo);
    }

    @Override
    public Optional<ConnectedRepository> findByUserIdAndProviderAndProviderRepoId(Long userId, ScmProvider provider,
            String providerRepoId) {
        return repository.findByUserIdAndProviderAndProviderRepoId(
            userId, provider, providerRepoId
        );
    }
}
