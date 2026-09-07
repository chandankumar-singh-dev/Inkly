package com.inkly.inkly_backend.auth.service;

import com.inkly.inkly_backend.auth.dto.login.LoginUserRequestDto;
import com.inkly.inkly_backend.auth.dto.login.LoginUserResponseDto;
import com.inkly.inkly_backend.auth.dto.refresh_token.RefreshTokenRequestDto;
import com.inkly.inkly_backend.auth.dto.refresh_token.RefreshTokenResponseDto;
import com.inkly.inkly_backend.auth.dto.register.RegisterUserRequestDto;
import com.inkly.inkly_backend.auth.dto.register.RegisterUserResponseDto;
import com.inkly.inkly_backend.auth.entity.RefreshToken;
import com.inkly.inkly_backend.auth.exception.*;
import com.inkly.inkly_backend.auth.mapper.LoginUserMapper;
import com.inkly.inkly_backend.auth.mapper.RegisterUserMapper;
import com.inkly.inkly_backend.auth.repository.RefreshTokenRepository;
import com.inkly.inkly_backend.auth.security.CustomUserDetails;
import com.inkly.inkly_backend.user.entity.User;
import com.inkly.inkly_backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RegisterUserMapper registerUserMapper;
    private final LoginUserMapper loginUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final RefreshTokenHasher refreshTokenHasher;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(UserRepository userRepository,
                       RegisterUserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       LoginUserMapper loginUserMapper,
                       RefreshTokenGenerator refreshTokenGenerator,
                       RefreshTokenHasher refreshTokenHasher,
                       RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.registerUserMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.loginUserMapper = loginUserMapper;
        this.refreshTokenGenerator = refreshTokenGenerator;
        this.refreshTokenHasher = refreshTokenHasher;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RegisterUserResponseDto register(RegisterUserRequestDto registerUserRequest) {
        User user = registerUserMapper.toEntity(registerUserRequest);
        user.setPasswordHash(passwordEncoder.encode(registerUserRequest.getPassword()));
        boolean existsUsername = userRepository.existsByUsername(user.getUsername());
        boolean existsEmail = userRepository.existsByEmail(user.getEmail());
        if(existsUsername) throw new UsernameAlreadyExistException("Username already exist in database");
        if(existsEmail) throw new EmailAlreadyExistException("Email already exist in database");
        User savedUser = userRepository.save(user);

        Instant now = Instant.now();
        String token = jwtService.generateToken(new CustomUserDetails(user));
        String rawRefreshToken = createRefreshToken(user, now, now.plus(15, ChronoUnit.DAYS));
        RegisterUserResponseDto responseDto = registerUserMapper.toDto(savedUser);
        responseDto.setAccessToken(token);
        responseDto.setRefreshToken(rawRefreshToken);
        return responseDto;
    }


    @Transactional
    public LoginUserResponseDto login(LoginUserRequestDto loginUserRequest) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginUserRequest.getUsername(),
                        loginUserRequest.getPassword()
                )
        );

        Instant now = Instant.now();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String rawRefreshToken = createRefreshToken(userDetails.getUser(),now,now.plus(15,ChronoUnit.DAYS));
        String token = jwtService.generateToken(userDetails);
        LoginUserResponseDto responseDto = loginUserMapper.toDto(userDetails.getUser());
        responseDto.setAccessToken(token);
        responseDto.setRefreshToken(rawRefreshToken);

        return responseDto;
    }

    @Transactional
    public String createRefreshToken(User user, Instant createdAt, Instant expiresAt) {
        String rawRefreshToken = refreshTokenGenerator.generate();
        String refreshTokenHash = refreshTokenHasher.hash(rawRefreshToken);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setCreatedAt(createdAt);
        refreshToken.setExpiresAt(expiresAt);
        refreshToken.setFamilyId(UUID.randomUUID());
        refreshToken.setTokenHash(refreshTokenHash);
        refreshTokenRepository.save(refreshToken);
        return rawRefreshToken;
    }


    // TODO revoked entire family is not working
    @Transactional
    public RefreshTokenResponseDto rotationRefreshToken(RefreshTokenRequestDto refreshTokenRequestDto){
        String refreshTokenHash = refreshTokenHasher.hash(refreshTokenRequestDto.getRefreshToken());
        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(refreshTokenHash).orElseThrow(
                () -> new RefreshHashTokenNotFoundException("Refresh token not found")
        );

        Instant now = Instant.now();
        if(now.isAfter(refreshToken.getExpiresAt())) throw new RefreshTokenAlreadyExpiredException("Token is already expired. Please login again.");

        if(refreshToken.getRevokedAt() != null) {
            List<RefreshToken> refreshTokens = refreshTokenRepository.findByFamilyId(refreshToken.getFamilyId());
            for (RefreshToken token: refreshTokens) {
                if(token.getRevokedAt() == null) {
                    token.setRevokedAt(now);
                }
            }
            throw new RefreshTokenAlreadyRevokedException("Token is already revoked");
        }

        refreshToken.setRevokedAt(now);
        String newRawRefreshToken = refreshTokenGenerator.generate();
        String newHashRefreshToken = refreshTokenHasher.hash(newRawRefreshToken);

        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setFamilyId(refreshToken.getFamilyId());
        newRefreshToken.setTokenHash(newHashRefreshToken);
        newRefreshToken.setCreatedAt(now);
        newRefreshToken.setExpiresAt(now.plus(15,ChronoUnit.DAYS));
        newRefreshToken.setUser(refreshToken.getUser());
        refreshTokenRepository.save(newRefreshToken);

        CustomUserDetails userDetails = new CustomUserDetails(refreshToken.getUser());
        String accessToken = jwtService.generateToken(userDetails);
        RefreshTokenResponseDto responseDto = new RefreshTokenResponseDto();
        responseDto.setRefreshToken(newRawRefreshToken);
        responseDto.setAccessToken(accessToken);

        return responseDto;
    }
}
