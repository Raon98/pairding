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
    name = "repo_ai_prompt_settings",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_ai_prompt_connected_repo",
            columnNames = {"connected_repository_id"}
        )
    },
    indexes = {
        @Index(name = "idx_ai_prompt_repo", columnList = "connected_repository_id")
    }
)
public class RepoAiPromptSetting extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "connected_repository_id", nullable = false)
  private Long connectedRepositoryId;
  @Column(nullable = false)
  private boolean enabled;
  @Lob
  @Column(name = "system_prompt", nullable = false)
  private String systemPrompt;
  @Lob
  @Column(name = "review_prompt_template", nullable = false)
  private String reviewPromptTemplate;
  
  private RepoAiPromptSetting(Long connectedRepositoryId,
                              boolean enabled,
                              String systemPrompt,
                              String reviewPromptTemplate) {
    this.connectedRepositoryId = connectedRepositoryId;
    this.enabled = enabled;
    this.systemPrompt = systemPrompt;
    this.reviewPromptTemplate = reviewPromptTemplate;
  }

  public static RepoAiPromptSetting create(Long connectedRepositoryId,
                                           String systemPrompt,
                                           String reviewPromptTemplate) {
    return new RepoAiPromptSetting(
        connectedRepositoryId,
        true,
        systemPrompt,
        reviewPromptTemplate
    );
  }

  public void update(String systemPrompt,
                     String reviewPromptTemplate,
                     boolean enabled) {
    this.systemPrompt = systemPrompt;
    this.reviewPromptTemplate = reviewPromptTemplate;
    this.enabled = enabled;
  }
}