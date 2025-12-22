package com.pairding.scm.infrastructure.db.store;

import java.util.Optional;

import com.pairding.scm.domain.model.ConnectedRepository;
import com.pairding.scm.domain.repository.ConnectedRepoStore;

public class ConnectedRepoStoreImpl implements ConnectedRepoStore {

    @Override
    public Optional<ConnectedRepository> findByUserIdAndProviderAndFullName(Long userId, String provider,
            String fullName) {

        throw new UnsupportedOperationException("Unimplemented method 'findByUserIdAndProviderAndFullName'");
    }

    @Override
    public ConnectedRepository save(ConnectedRepository repo) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
    
}
