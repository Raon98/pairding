package com.pairding.scm.infrastructure.scmClient.github.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GithubEmailResponse {
    private String email;
    private boolean primary;
    private boolean verified;
    private String visibility;
}
