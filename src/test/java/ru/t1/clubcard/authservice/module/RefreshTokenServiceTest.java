package ru.t1.clubcard.authservice.module;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.clubcard.authservice.exception.RefreshTokenExpirationException;
import ru.t1.clubcard.authservice.exception.TokenNotFoundException;
import ru.t1.clubcard.authservice.model.ClubMember;
import ru.t1.clubcard.authservice.model.RefreshToken;
import ru.t1.clubcard.authservice.repository.RefreshTokenRepository;
import ru.t1.clubcard.authservice.service.ClubMemberService;
import ru.t1.clubcard.authservice.service.RefreshTokenService;
import ru.t1.clubcard.authservice.stubs.ClubMemberStub;
import ru.t1.clubcard.authservice.stubs.RefreshTokenStub;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private ClubMemberService clubMemberService;
    @InjectMocks
    private RefreshTokenService refreshTokenService;
    private RefreshToken refreshToken;
    private ClubMember clubMember;

    @BeforeEach
    void setUp() {
        refreshToken = RefreshTokenStub.getRefreshTokenStub();
        clubMember = ClubMemberStub.getClubMEmberStub();
    }

    @Test
    void getByTokenPositiveTest() {
        when(refreshTokenRepository.findByToken(refreshToken.getToken())).thenReturn(Optional.of(refreshToken));

        assertNotNull(refreshTokenService.getByToken(refreshToken.getToken()));
        verify(refreshTokenRepository, times(1)).findByToken(refreshToken.getToken());
    }

    @Test
    void getByTokenThrowsException() {
        when(refreshTokenRepository.findByToken(refreshToken.getToken())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(TokenNotFoundException.class,
                () -> refreshTokenService.getByToken(refreshToken.getToken()));
        assertNotNull(exception);
        verify(refreshTokenRepository, times(1)).findByToken(refreshToken.getToken());
    }

    @Test
    void createPositiveTest() {
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(refreshToken);
        when(clubMemberService.getByUsername(clubMember.getEmail())).thenReturn(clubMember);

        assertNotNull(refreshTokenService.create(clubMember.getEmail()));
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
        verify(clubMemberService, times(1)).getByUsername(clubMember.getEmail());
    }

    @Test
    void verifyExpirationPositiveTest() {
        assertNotNull(refreshTokenService.verifyExpiration(refreshToken));
    }

    @Test
    void verifyExpirationThrowsExceptionTest() {
        refreshToken.setExpiryDate(
                Instant.ofEpochMilli(System.currentTimeMillis() - 1000)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
        );
        RuntimeException exception = assertThrows(RefreshTokenExpirationException.class,
                () -> refreshTokenService.verifyExpiration(refreshToken));
        assertNotNull(exception);
    }
}
