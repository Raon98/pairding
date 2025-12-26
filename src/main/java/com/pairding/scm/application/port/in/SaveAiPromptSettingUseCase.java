package com.pairding.scm.application.port.in;

import com.pairding.scm.domain.model.RepoAiPromptSetting;
import com.pairding.scm.domain.repository.RepoAiPromptSettingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveAiPromptSettingUseCase {

    private final RepoAiPromptSettingStore store;

    @Transactional
    public void execute(Long connectedRepositoryId,
                        String systemPrompt,
                        String reviewPromptTemplate,
                        boolean codeStyleImprove,
                        boolean bugFix,
                        boolean refactoring,
                        boolean enabled) {

        store.findByConnectedRepositoryId(connectedRepositoryId)
            .ifPresentOrElse(
                setting -> setting.update(
                    systemPrompt,
                    reviewPromptTemplate,
                    codeStyleImprove,
                    bugFix,
                    refactoring,
                    enabled
                ),
                () -> store.save(
                    RepoAiPromptSetting.create(
                        connectedRepositoryId,
                        systemPrompt,
                        reviewPromptTemplate,
                        codeStyleImprove,
                        bugFix,
                        refactoring
                    )
                )
            );
    }
}
