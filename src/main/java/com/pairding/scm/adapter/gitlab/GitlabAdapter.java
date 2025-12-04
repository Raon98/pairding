package com.pairding.scm.adapter.gitlab;

import com.pairding.scm.port.SourceControlService;
import com.pairding.scm.service.dto.ChangedFile;
import com.pairding.scm.service.dto.RepositoryInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GitlabAdapter implements SourceControlService {

    @Override
    public List<RepositoryInfo> getRepositories(Long userId) {
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
}
