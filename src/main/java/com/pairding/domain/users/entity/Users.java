package com.pairding.domain.users.entity;

import com.oneRunning.domain.users.enums.Gender;
import com.oneRunning.domain.users.enums.OAuthProvider;
import com.pairding.global.entity.BaseEntity;
import com.pairding.global.security.TsidGenerator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Schema(description = "유저 테이블")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class Users extends BaseEntity {

    @Id
    @Schema(description = "유저 TSID")
    private Long id;

    @Schema(description = "닉네임", example = "러너철이")
    private String nickname;

    @Schema(description = "이메일", example = "test@gmail.com")
    private String email;

    @Enumerated(EnumType.STRING)
    @Schema(description = "OAuth 제공자", example = "GOOGLE")
    private OAuthProvider oAuthProvider;

    @Enumerated(EnumType.STRING)
    @Schema(description = "성별", example = "M")
    private Gender gender;

    @Schema(description = "나이", example = "29")
    private int age;

    @Schema(description = "현재 평균 페이스 (km/min)", example = "5.10")
    private float current_pace;

    @Schema(description = "목표 페이스 (km/min)", example = "4.50")
    private float target_pace;

    @Schema(description = "마라톤 기록", example = "03:42:10")
    private String marathon_record;

    @Schema(description = "러닝 목표", example = "서브 4 도전!")
    private String running_goal;

    @Schema(description = "자기소개")
    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;

    @Schema(description = "수정일")
    private LocalDateTime updatedAt;

    @Builder
    public Users(String name){
        this.id = TsidGenerator.nextId();
    }

}
