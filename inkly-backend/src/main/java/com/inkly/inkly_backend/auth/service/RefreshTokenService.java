package com.inkly.inkly_backend.auth.service;

import com.inkly.inkly_backend.auth.entity.RefreshToken;
import com.inkly.inkly_backend.auth.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW
    )
    public void revokedEntireFamily(UUID familyId) {
        Instant now = Instant.now();
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByFamilyId(familyId);
        for (RefreshToken token: refreshTokens) {
            if(token.getRevokedAt() == null) {
                token.setRevokedAt(now);
            }
        }
    }
}
