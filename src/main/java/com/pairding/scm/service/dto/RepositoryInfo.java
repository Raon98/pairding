package com.pairding.scm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RepositoryInfo {
    private Long id;
    private String owner;
    private String name;
    private String fullName;
    private boolean isPrivate;
}
