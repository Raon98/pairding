package com.pairding.scm.infrastructure.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pairding.scm.domain.enums.ScmProvider;
import com.pairding.scm.domain.model.ConnectedRepository;

public interface ConnectedRepositoryJpaRepository extends JpaRepository<ConnectedRepository,Long>{
   boolean existsByUserIdAndProviderAndProviderRepoId(Long userId, ScmProvider provider, String providerRepoId);
  
   Optional<ConnectedRepository> findByUserIdAndProviderAndProviderRepoId(Long userId, ScmProvider provider, String providerRepoId);

   List<ConnectedRepository> findAllByUserId(Long userId);

   List<ConnectedRepository> findAllByUserIdAndProvider(Long userId, ScmProvider provider);
}
