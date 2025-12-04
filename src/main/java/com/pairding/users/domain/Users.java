package com.pairding.users.domain;


import com.pairding.global.domain.BaseEntity;
import com.pairding.global.security.TsidGenerator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Schema(description = "유저 테이블")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class Users extends BaseEntity {

    @Id
    @Schema(description = "유저 TSID")
    private Long id;

    @Column(length = 100)
    @Schema(description = "사용자 이메일(있을 경우)")
    private String email;

    @Column(length = 100)
    @Schema(description = "사용자 표시 이름")
    private String name;

    @Builder
    public Users(String email, String name) {
        this.id = TsidGenerator.nextId();
        this.email = email;
        this.name = name;
    }

}
