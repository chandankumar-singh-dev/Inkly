package com.inkly.inkly_backend.auth.entity;

import com.inkly.inkly_backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "family_id", nullable = false)
    private UUID familyId;


    @Column(name = "token_hash",nullable = false)
    private String tokenHash;

    @Column(name = "created_at",nullable = false)
    private Instant createdAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(name = "expires_at",nullable = false)
    private Instant expiresAt;

}
