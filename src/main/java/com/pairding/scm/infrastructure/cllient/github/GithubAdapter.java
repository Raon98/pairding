package com.pairding.scm.infrastructure.cllient.github;

import com.pairding.scm.application.dto.ChangedFile;
import com.pairding.scm.application.dto.RepositoryInfo;
import com.pairding.scm.application.port.out.SourceControlPort;
import com.pairding.scm.infrastructure.cllient.github.dto.GithubCommitResponse;
import com.pairding.scm.infrastructure.cllient.github.dto.GithubContentResponse;
import com.pairding.scm.infrastructure.cllient.github.dto.GithubRefResponse;
import com.pairding.scm.infrastructure.cllient.github.dto.GithubRepoResponse;
import com.pairding.users.infrastructure.db.repository.UserConnectionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GithubAdapter implements SourceControlPort {
    private final GithubApiClient githubApiClient;
    private final UserConnectionRepository connectionRepository;

    @Override
    public List<RepositoryInfo> getRepositories(Long userId) {
        String token = getToken(userId);
        GithubRepoResponse[] data = githubApiClient.get("/user/repos", token, GithubRepoResponse[].class);

        return Arrays.stream(data)
                .map(r -> new RepositoryInfo(r.getId(),r.getOwner().getLogin(), r.getName(), r.getFullName(), r.isPrivate()))
                .toList();
    }

    @Override
    public void createBranch(Long userId, String owner, String repo, String branchName) {
        String token = getToken(userId);

        String refUrl = String.format("/repos/%s/%s/git/ref/heads/main", owner, repo);
        GithubRefResponse ref = githubApiClient.get(refUrl, token, GithubRefResponse.class);

        String createUrl = String.format("/repos/%s/%s/git/refs", owner, repo);

        Map<String, String> body = Map.of(
                "ref", "refs/heads/" + branchName,
                "sha", ref.getObject().getSha()
        );

        githubApiClient.post(createUrl, token, body, Void.class);
    }

    @Override
    public void createPullRequest(Long userId, String owner, String repo, String title, String description) {
        String token = getToken(userId);

        String url = String.format("/repos/%s/%s/pulls", owner, repo);

        Map<String, Object> body = Map.of(
                "title", title,
                "body", description,
                "head", "ai-auto-branch",
                "base", "main"
        );

        githubApiClient.post(url, token, body, Void.class);
    }

    @Override
    public String getFileContent(Long userId, String owner, String repo, String filePath) {
        String token = getToken(userId);

        String url = String.format("/repos/%s/%s/contents/%s", owner, repo, filePath);

        GithubContentResponse res =
                githubApiClient.get(url, token, GithubContentResponse.class);

        return new String(Base64.getDecoder().decode(res.getContent()));
    }

    @Override
    public List<ChangedFile> getChangedFiles(Long userId, String owner, String repo, String commitSha) {
        String token = getToken(userId);

        String url = String.format("/repos/%s/%s/commits/%s", owner, repo, commitSha);

        GithubCommitResponse response =
                githubApiClient.get(url, token, GithubCommitResponse.class);

        return response.getFiles().stream()
                .map(f -> new ChangedFile(
                        f.getFilename(),
                        f.getStatus(),
                        f.getPatch(),
                        null
                ))
                .toList();
    }

    private String getToken(Long userId) {
        return connectionRepository.findByUserIdAndProvider(userId, "github")
                .orElseThrow(() -> new RuntimeException("GitHub not connected"))
                .getEncryptedAccessToken();
    }
}
