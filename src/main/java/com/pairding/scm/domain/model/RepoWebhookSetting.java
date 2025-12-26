package com.pairding.scm.domain.model;
import com.pairding.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "repo_webhook_settings",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_webhook_setting_connected_repo",
            columnNames = {"connected_repository_id"}
        )
    },
    indexes = {
        @Index(name = "idx_webhook_repo", columnList = "connected_repository_id")
    }
)
public class RepoWebhookSetting extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "connected_repository_id", nullable = false)
  private Long connectedRepositoryId;
  @Column(nullable = true)
  private boolean enabled;
  @Column(name = "branch_filter", length = 100)
  private String branchFilter;

  private RepoWebhookSetting(Long connectedRepositoryId, boolean enabled, String branchFilter) {
    this.connectedRepositoryId = connectedRepositoryId;
    this.enabled = enabled;
    this.branchFilter = branchFilter;
  }

  /** 최초 생성(PUSH 기본) */
  public static RepoWebhookSetting create(Long connectedRepositoryId, String branchFilter) {
    return new RepoWebhookSetting(connectedRepositoryId, true, branchFilter);
  }

  /** 수정 */
  public void update(String branchFilter, boolean enabled) {
    this.branchFilter = branchFilter;
    this.enabled = enabled;
  }
}