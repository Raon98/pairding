package com.pairding.global.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Transient;
import lombok.Getter;
import org.springframework.data.domain.Persistable;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Persistable<Long> {

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }

}






