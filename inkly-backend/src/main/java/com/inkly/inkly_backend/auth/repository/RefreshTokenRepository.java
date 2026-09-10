package com.inkly.inkly_backend.auth.repository;

import com.inkly.inkly_backend.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);
    List<RefreshToken> findByFamilyId(UUID familyId);

    @Modifying
    @Query("""
        UPDATE RefreshToken r
                set r.revokedAt = :revokedAt
                where r.user.id = :userid
        """
    )
    int revokeAllByUserId(@Param("userid") Long userId, @Param("revokedAt") Instant revokedAt);
}
