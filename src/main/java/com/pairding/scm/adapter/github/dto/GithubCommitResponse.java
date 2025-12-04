package com.pairding.scm.adapter.github.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GithubCommitResponse {

    private List<File> files;

    @Getter
    public static class File {
        private String filename;
        private String status;
        private String patch;
    }
}