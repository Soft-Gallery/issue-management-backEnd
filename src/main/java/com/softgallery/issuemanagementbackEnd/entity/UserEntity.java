package com.softgallery.issuemanagementbackEnd.entity;

import com.softgallery.issuemanagementbackEnd.util.custom_annotation.IDRule;
import com.softgallery.issuemanagementbackEnd.util.custom_annotation.PasswordRule;
import com.softgallery.issuemanagementbackEnd.util.Role;
import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public abstract class UserEntity {
    @Id
    @NonNull
    @IDRule
    @Column(name = "user_id", unique = true)
    private String userId;

    @NonNull
    private String name;

    @NonNull
//    @Email(message = "이메일 주소 형식이 올바르지 않습니다")
    private String email;

    @NonNull
    @PasswordRule
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;
}
