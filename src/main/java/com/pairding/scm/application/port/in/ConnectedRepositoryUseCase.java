package com.pairding.scm.application.port.in;

import com.pairding.scm.application.dto.RepositoryInfo;
import com.pairding.scm.application.port.SourceControlService;
import com.pairding.scm.application.resolver.SourceControlServiceResolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectedRepositoryUseCase {

    private final SourceControlServiceResolver resolver;

    public List<RepositoryInfo> getRepositories(String provider, Long userId) {
        SourceControlService scm = resolver.resolve(provider);
        return scm.getRepositories(userId);
    }
}
