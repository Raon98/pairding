package com.pairding.users.repository;

import com.pairding.users.domain.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection, Long> {

    Optional<List<UserConnection>> findByUserId(Long userId);

    Optional<UserConnection> findByUserIdAndProvider(Long userId, String provider);

    boolean existsByUserIdAndProvider(Long userId, String provider);
}
