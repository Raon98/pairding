package com.pairding.scm.adapter.github.dto;

import lombok.Getter;

@Getter
public class GithubRefResponse {
    private RefObject object;

    @Getter
    public static class RefObject {
        private String sha;
    }
}
