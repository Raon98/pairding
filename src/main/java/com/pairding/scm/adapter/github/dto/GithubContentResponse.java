package com.pairding.scm.adapter.github.dto;

import lombok.Getter;

@Getter
public class GithubContentResponse {
    private String content;
    private String encoding;
    private String path;
    private String sha;
}
