package com.pairding.scm.domain.model;

import com.pairding.global.domain.BaseTimeEntity;
import com.pairding.scm.domain.enums.ScmProvider;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "connected_repositories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_conn_repo_user_provider_full",
                        columnNames = {"user_id", "provider", "full_name"}
                )
        },
        indexes = {
                @Index(name = "idx_conn_repo_user", columnList = "user_id"),
                @Index(name = "idx_conn_repo_user_provider", columnList = "user_id, provider"),
                @Index(name = "idx_conn_repo_full_name", columnList = "full_name"),
                @Index(name = "idx_conn_repo_user_created", columnList = "user_id, created_at")
        }
)
@Schema(description = "사용자가 연결한 SCM 저장소 정보")
public class ConnectedRepository extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ScmProvider provider;

    @Column(name = "provider_repo_id", nullable = false)
    private Long providerRepoId;

    @Column(nullable = false, length = 200)
    private String owner;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "full_name", nullable = false, length = 500)
    private String fullName;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Column(name = "default_branch", length = 100)
    private String defaultBranch;


    @Builder
    public ConnectedRepository(Long id,
                               Long userId,
                               ScmProvider provider,
                               Long providerRepoId,
                               String owner,
                               String name,
                               String fullName,
                               boolean isPrivate,
                               String defaultBranch) {
        this.id = id;
        this.userId = userId;
        this.provider = provider;
        this.providerRepoId = providerRepoId;
        this.owner = owner;
        this.name = name;
        this.fullName = fullName;
        this.isPrivate = isPrivate;
        this.defaultBranch = defaultBranch;
    }
}
