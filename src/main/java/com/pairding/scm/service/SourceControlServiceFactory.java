package com.pairding.scm.service;

import com.pairding.scm.adapter.github.GithubAdapter;
import com.pairding.scm.adapter.gitlab.GitlabAdapter;
import com.pairding.scm.port.SourceControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceControlServiceFactory {
    private final GithubAdapter githubAdapter;
    private final GitlabAdapter gitlabAdapter;

    public SourceControlService getService(String provider) {

        if (provider.equalsIgnoreCase("github")) {
            return githubAdapter;
        }else if (provider.equalsIgnoreCase("gitlab")) {
            return gitlabAdapter;
        }

        throw new IllegalStateException("지원하지 않는 Provider: " + provider);
    }
}
