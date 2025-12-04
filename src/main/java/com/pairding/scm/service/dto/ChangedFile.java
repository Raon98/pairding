package com.pairding.scm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChangedFile {

    private final String filePath;

    private final String changeType;

    private final String patch;

    private final String fullContent;
}
