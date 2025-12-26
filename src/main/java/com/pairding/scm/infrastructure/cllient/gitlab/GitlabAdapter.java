package com.pairding.scm.infrastructure.cllient.gitlab;

import com.pairding.scm.application.dto.ChangedFile;
import com.pairding.scm.application.dto.RepositoryInfoResult;
import com.pairding.scm.application.port.out.SourceControlPort;
import com.pairding.scm.domain.enums.ScmProvider;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GitlabAdapter implements SourceControlPort {

    @Override
    public List<RepositoryInfoResult> getRepositories(Long userId) {
        return List.of();
    }

    @Override
    public void createBranch(Long userId, String owner, String repo, String branchName) {

    }

    @Override
    public void createPullRequest(Long userId, String owner, String repo, String title, String description) {

    }

    @Override
    public String getFileContent(Long userId, String owner, String repo, String filePath) {
        return "";
    }

    @Override
    public List<ChangedFile> getChangedFiles(Long userId, String owner, String repo, String commitSha) {
        return List.of();
    }

    @Override
    public ScmProvider supports() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'supports'");
    }

    @Override
    public CreateWebhookResult createPushWebhook(CreateWebhookCommand command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPushWebhok'");
    }
}
