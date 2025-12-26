package com.pairding.scm.application.resolver;

import com.pairding.scm.application.port.out.SourceControlPort;
import com.pairding.scm.domain.enums.ScmProvider;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceControlServiceResolver {
    private final List<SourceControlPort> ports;

    public SourceControlPort resolve(ScmProvider provider) {
        return ports.stream()
                .filter(port -> port.supports() == provider)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("지원하지않는 Provider : " + provider));
    }
}
