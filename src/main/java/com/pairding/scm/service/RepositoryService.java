package com.pairding.scm.service;

import com.pairding.scm.port.SourceControlService;
import com.pairding.scm.service.dto.RepositoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryService {

    private final SourceControlServiceFactory factory;

    public List<RepositoryInfo> getRepositories(String provider, Long userId) {
        SourceControlService scm = factory.getService(provider);
        return scm.getRepositories(userId);
    }
}
