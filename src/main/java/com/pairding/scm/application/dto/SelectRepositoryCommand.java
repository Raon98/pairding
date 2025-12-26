package com.pairding.scm.application.dto;

public record SelectRepositoryCommand(
    Long userId,
    String provider,
    String providerRepoId,
    String owner,
    String name,
    String fullName,
    boolean isPrivate,
    String defaultBranch
) {}
