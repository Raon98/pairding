package com.pairding.scm.application.port.out;

import java.util.List;

import com.pairding.scm.application.dto.ChangedFile;
import com.pairding.scm.application.dto.RepositoryInfoResult;
import com.pairding.scm.domain.enums.ScmProvider;

public interface SourceControlPort {
    ScmProvider supports();

    CreateWebhookResult createPushWebhook(CreateWebhookCommand command);

    record CreateWebhookCommand(
        String accessToken,
        String owner,
        String repoName,
        String callbackUrl,
        String secret
    ){}

    record CreateWebhookResult(
        String providerWebhookId
    ){}
    
    List<RepositoryInfoResult> getRepositories(Long userId);

    void createBranch(Long userId, String owner, String repo, String branchName);

    void createPullRequest(Long userId, String owner, String repo, String title, String description);

    String getFileContent(Long userId, String owner, String repo, String filePath);

    List<ChangedFile> getChangedFiles(Long userId, String owner, String repo, String commitSha);


}

