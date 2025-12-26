package com.pairding.scm.application.dto;

import com.pairding.scm.domain.enums.ConnectedRepoStatus;

public record SelectRepositoryResult(
    Long connectedRepositoryId,
    ConnectedRepoStatus status,
    RepositorySetupNextStep nextStep
) {}
