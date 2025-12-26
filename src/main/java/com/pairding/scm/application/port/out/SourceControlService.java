package com.pairding.scm.application.port.out;

import java.util.List;

import com.pairding.scm.application.dto.ChangedFile;
import com.pairding.scm.application.dto.RepositoryInfo;

public interface SourceControlService {
    /*원격저장소에서 repo 가져오기*/
    List<RepositoryInfo> getRepositories(Long userId);

    void createBranch(Long userId, String owner, String repo, String branchName);

    void createPullRequest(Long userId, String owner, String repo, String title, String description);

    String getFileContent(Long userId, String owner, String repo, String filePath);

    List<ChangedFile> getChangedFiles(Long userId, String owner, String repo, String commitSha);


}

