package com.pairding.scm.domain;

import com.pairding.global.domain.BaseTimeEntity;

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
public class RepositoryEntity extends BaseTimeEntity{

    @Id
    @Schema(description = "내부 저장소 ID(TSID 또는 Long)", example = "1000000000001")
    private Long id;

    @Column(name = "user_id", nullable = false)
    @Schema(description = "서비스 사용자 ID", example = "1000000000000")
    private Long userId;

    @Column(nullable = false, length = 20)
    @Schema(description = "SCM Provider", example = "github", allowableValues = {"github", "gitlab"})
    private String provider;

    @Column(name = "provider_repo_id", nullable = false)
    @Schema(description = "Provider 내 저장소 ID(GitHub/GitLab)", example = "654321987")
    private Long providerRepoId;

    @Column(nullable = false, length = 200)
    @Schema(description = "저장소 소유자(owner)", example = "sungcheol")
    private String owner;

    @Column(nullable = false, length = 200)
    @Schema(description = "저장소 이름", example = "pairding-backend")
    private String name;

    @Column(name = "full_name", nullable = false, length = 500)
    @Schema(description = "저장소 전체 이름(owner/name)", example = "sungcheol/pairding-backend")
    private String fullName;

    @Column(name = "is_private", nullable = false)
    @Schema(description = "비공개 저장소 여부", example = "true")
    private boolean isPrivate;

    @Column(name = "default_branch", length = 100)
    @Schema(description = "기본 브랜치", example = "main")
    private String defaultBranch;

    @Builder
    public RepositoryEntity(Long id,
                               Long userId,
                               String provider,
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
