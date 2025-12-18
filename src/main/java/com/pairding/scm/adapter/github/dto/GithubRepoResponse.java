package com.pairding.scm.adapter.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


public class GithubRepoResponse {
    
    private Long id;
    private String name;
    
    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("private")
    private boolean privateRepo;
    
    private Owner owner;

    @JsonProperty("default_branch")
    private String defaultBranch;
    
    @JsonProperty("html_url")
    private String htmlUrl;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getFullName() { return fullName; }
    public Owner getOwner() { return owner; }
    public String getDefaultBranch() { return defaultBranch; }
    public String getHtmlUrl() { return htmlUrl; }
    public boolean isPrivate() { return privateRepo; }
    
    @Getter
    public static class Owner {
        private String login;
    }

    
}
