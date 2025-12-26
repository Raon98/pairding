package com.pairding.scm.application.resolver;

import com.pairding.scm.application.port.out.SourceControlService;
import com.pairding.scm.infrastructure.cllient.github.GithubAdapter;
import com.pairding.scm.infrastructure.cllient.gitlab.GitlabAdapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceControlServiceResolver {
    private final GithubAdapter githubAdapter;
    private final GitlabAdapter gitlabAdapter;

    public SourceControlService resolve(String provider) {

        if (provider.equalsIgnoreCase("github")) {
            return githubAdapter;
        }else if (provider.equalsIgnoreCase("gitlab")) {
            return gitlabAdapter;
        }

        throw new IllegalStateException("지원하지 않는 Provider: " + provider);
    }
}
