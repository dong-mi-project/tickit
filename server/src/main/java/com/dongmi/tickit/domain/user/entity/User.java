package com.dongmi.tickit.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

// 예시 엔티티입니다. 실제로는 도메인에 맞게 엔티티를 작성해야 합니다.
@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name = "PW", nullable = false)
    private String password;

    @Column(nullable = false)
    private String accountType = "U";

    @Column(nullable = false)
    private String phone;

    @Column(name = "PROFILE_IMG", length = 300)
    private String profileImg;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Builder
    public User(String email, String name, String password, String accountType, String phone, String profileImg) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.accountType = accountType != null ? accountType : "U"; // 기본값 방어 코드
        this.phone = phone;
        this.profileImg = profileImg;
    }
}
