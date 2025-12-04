package com.pairding.scm.port;

import com.pairding.scm.service.dto.ChangedFile;
import com.pairding.scm.service.dto.RepositoryInfo;

import java.util.List;

public interface SourceControlService {
    /*원격저장소에서 repo 가져오기*/
    List<RepositoryInfo> getRepositories(Long userId);

    void createBranch(Long userId, String owner, String repo, String branchName);

    void createPullRequest(Long userId, String owner, String repo, String title, String description);

    String getFileContent(Long userId, String owner, String repo, String filePath);

    List<ChangedFile> getChangedFiles(Long userId, String owner, String repo, String commitSha);


}

