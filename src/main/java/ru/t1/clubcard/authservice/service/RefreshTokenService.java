package ru.t1.clubcard.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.t1.clubcard.authservice.exception.RefreshTokenExpirationException;
import ru.t1.clubcard.authservice.exception.TokenNotFoundException;
import ru.t1.clubcard.authservice.model.ClubMember;
import ru.t1.clubcard.authservice.model.RefreshToken;
import ru.t1.clubcard.authservice.repository.RefreshTokenRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${security.jwt.expiration-time-refresh}")
    private long jwtExpiration;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ClubMemberService clubMemberService;


    public RefreshToken getByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(TokenNotFoundException::new);
    }

    public String create(String clubMemberEmail) {
        ClubMember clubMember = clubMemberService.getByUsername(clubMemberEmail);
        RefreshToken token = RefreshToken
                .builder()
                .clubMember(clubMember)
                .expiryDate(
                        Instant.ofEpochMilli(System.currentTimeMillis() + jwtExpiration)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                )
                .token(UUID.randomUUID().toString())
                .build();
        return refreshTokenRepository.save(token).getToken();
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpirationException();
        }
        return refreshToken;
    }

    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }
}
