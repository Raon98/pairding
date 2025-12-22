package com.pairding.scm.infrastructure.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pairding.scm.domain.model.ConnectedRepository;

public interface ConnectedRepositoryJpaRepository extends JpaRepository<ConnectedRepository,Long>{
    Optional<ConnectedRepository> findByUserIdAndProviderAndFullName(Long userId, String provider, String fullName);
}
