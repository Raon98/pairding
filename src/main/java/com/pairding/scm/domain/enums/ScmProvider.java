package com.pairding.scm.domain.enums;

public enum ScmProvider {
    GITHUB,GITLAB;

    public static ScmProvider from (String str) {
        return ScmProvider.valueOf(str.trim().toUpperCase());
    }
}
