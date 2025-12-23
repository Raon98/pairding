package com.pairding.users.domain.model;

import com.pairding.global.core.tsid.TsidGenerator;
import com.pairding.global.domain.BaseEntity;
import com.pairding.users.domain.enums.Role;

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
    private Long id;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    private String refreshToken;
    
    @Builder
    public Users(String email, String name) {
        this.id = TsidGenerator.nextId();
        this.email = email;
        this.name = name;
        this.role = Role.USER;
    }
    
    @Builder
    public Users(String email, String name,Role role) {
        this.id = TsidGenerator.nextId();
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

}
