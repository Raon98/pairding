package com.pairding.scm.application.port.in;

import com.pairding.scm.application.dto.RepositoryInfo;
import com.pairding.scm.application.dto.RepositoryInfoResult;
import com.pairding.scm.application.port.out.SourceControlPort;
import com.pairding.scm.application.resolver.SourceControlServiceResolver;
import com.pairding.scm.domain.enums.ScmProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRepositoriesUseCase {

    private final SourceControlServiceResolver resolver;

    public List<RepositoryInfoResult> execute(String provider, Long userId) {
        ScmProvider scmProvider = ScmProvider.from(provider);
        SourceControlPort scm = resolver.resolve(scmProvider);
        return scm.getRepositories(userId);
    }
}
