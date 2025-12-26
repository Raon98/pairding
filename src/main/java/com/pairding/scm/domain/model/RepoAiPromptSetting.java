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
  
  @Column(nullable = true)
  private boolean enabled;
  
  @Lob
  @Column(name = "system_prompt", nullable = false)
  private String systemPrompt;
  
  @Lob
  @Column(name = "review_prompt_template", nullable = false)
  private String reviewPromptTemplate;

  @Column(name = "opt_code_style", nullable = false)
  private boolean codeStyleImprove;

  @Column(name = "opt_bug_fix", nullable = false)
  private boolean bugFix;

  @Column(name = "opt_refactoring", nullable = false)
  private boolean refactoring;

  private RepoAiPromptSetting(Long connectedRepositoryId,
                              boolean enabled,
                              String systemPrompt,
                              String reviewPromptTemplate,
                              boolean codeStyleImprove,
                              boolean bugFix,
                              boolean refactoring
                            ) {
    this.connectedRepositoryId = connectedRepositoryId;
    this.enabled = enabled;
    this.systemPrompt = systemPrompt;
    this.reviewPromptTemplate = reviewPromptTemplate;
    this.codeStyleImprove = codeStyleImprove;
    this.bugFix = bugFix;
    this.refactoring = refactoring;

  }

  public static RepoAiPromptSetting create(Long connectedRepositoryId,
                                           String systemPrompt,
                                           String reviewPromptTemplate,
                                           boolean codeStyleImprove,
                                           boolean bugFix,
                                           boolean refactoring
                                          ) {
    return new RepoAiPromptSetting(
        connectedRepositoryId,
        true,
        systemPrompt,
        reviewPromptTemplate,
        codeStyleImprove,
        bugFix,
        refactoring
    );
  }

  public void update(String systemPrompt,
                     String reviewPromptTemplate,
                     boolean codeStyleImprove,
                     boolean bugFix,
                     boolean refactoring,
                     boolean enabled) {
    this.systemPrompt = systemPrompt;
    this.reviewPromptTemplate = reviewPromptTemplate;
    this.codeStyleImprove = codeStyleImprove;
    this.bugFix = bugFix;
    this.refactoring = refactoring;
    this.enabled = enabled;
  }
}