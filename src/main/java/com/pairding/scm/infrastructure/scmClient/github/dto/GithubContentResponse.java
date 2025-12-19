package com.pairding.scm.infrastructure.scmClient.github.dto;

import lombok.Getter;

@Getter
public class GithubContentResponse {
    private String content;
    private String encoding;
    private String path;
    private String sha;
}
