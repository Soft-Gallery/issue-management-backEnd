package com.softgallery.issuemanagementbackEnd.entity;

import com.softgallery.issuemanagementbackEnd.util.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public abstract class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String password;

    // @Enumerated(EnumType.STRING): DB에는 Enum 타입의 이름(ex admin)이 들어감.
    // 프로그래밍시에는 열거형으로 처리됨
    // 대신 role 타입 이외의 값이 들어오는것 못 막음
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
