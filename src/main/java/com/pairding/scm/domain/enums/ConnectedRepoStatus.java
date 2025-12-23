package com.pairding.scm.domain.enums;

public enum ConnectedRepoStatus {
  DRAFT,ACTIVE;

  public boolean isDraft(){
    return this == DRAFT;
  }
    
  public boolean isActive(){
    return this == ACTIVE;
  }
}
