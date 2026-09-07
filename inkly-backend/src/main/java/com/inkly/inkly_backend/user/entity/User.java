package com.inkly.inkly_backend.user.entity;

import com.inkly.inkly_backend.user.attribute_converter.BooleanToStringConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(
            name = "username",
            nullable = false,
            unique = true,
            length = 50
    )
    private String username;


    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "password_hash",
            nullable = false
    )
    private String passwordHash;


    @Column(
            name = "full_name",
            nullable = false,
            length = 100
    )
    private String name;


    @Column(
            name = "profile_img_url"
    )
    private String profileImageUrl;


    @Column(name = "is_account_non_expired", nullable = false)
    @Convert(converter = BooleanToStringConverter.class)
    private Boolean isAccountNonExpired = true;

    @Column(name = "is_account_non_locked", nullable = false)
    @Convert(converter = BooleanToStringConverter.class)
    private Boolean isAccountNonLocked = true;

    @Column(name = "is_credentials_non_expired", nullable = false)
    @Convert(converter = BooleanToStringConverter.class)
    private Boolean isCredentialsNonExpired = true;

    @Column(name = "is_enabled", nullable = false)
    @Convert(converter = BooleanToStringConverter.class)
    private Boolean isEnabled = true;


    @Column(
            name = "created_at",
            nullable = false
    )
    private Instant createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private Instant updatedAt;



    @PrePersist
    protected void setTimestamp() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void updateTimestamp() {
        this.updatedAt = Instant.now();
    }
}
