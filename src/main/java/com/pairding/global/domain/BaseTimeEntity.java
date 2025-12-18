package com.pairding.global.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Schema(description = "엔티티 공통 시간 정보")
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name="created_at", nullable = false, updatable = false)
    @Schema(description = "생성 일시", example = "2025-12-18T10:15:30")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
     @Schema(description = "최종 수정 일시", example = "2025-12-18T11:20:45")
    private LocalDateTime updatedAt;
}






