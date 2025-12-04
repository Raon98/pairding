package com.pairding.scm.adapter.github.dto;

import lombok.Getter;

@Getter
public class GithubRepoResponse {
    private Long id;
    private String name;
    private String fullName;
    private boolean _private;

    public boolean isPrivate() { return _private; }
}
