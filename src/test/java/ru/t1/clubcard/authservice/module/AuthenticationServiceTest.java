package ru.t1.clubcard.authservice.module;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.t1.clubcard.authservice.dto.in.ClubMemberLoginRequest;
import ru.t1.clubcard.authservice.dto.in.ClubMemberRegisterRequest;
import ru.t1.clubcard.authservice.dto.in.RefreshTokenRequest;
import ru.t1.clubcard.authservice.dto.out.ClubMemberLoginResponse;
import ru.t1.clubcard.authservice.dto.out.ClubMemberRegisterResponse;
import ru.t1.clubcard.authservice.model.ClubMember;
import ru.t1.clubcard.authservice.model.RefreshToken;
import ru.t1.clubcard.authservice.service.AuthenticationService;
import ru.t1.clubcard.authservice.service.ClubMemberService;
import ru.t1.clubcard.authservice.service.JwtService;
import ru.t1.clubcard.authservice.service.RefreshTokenService;
import ru.t1.clubcard.authservice.stubs.ClubMemberLoginRequestStub;
import ru.t1.clubcard.authservice.stubs.ClubMemberRegisterRequestStub;
import ru.t1.clubcard.authservice.stubs.ClubMemberRegisterResponseStub;
import ru.t1.clubcard.authservice.stubs.ClubMemberStub;
import ru.t1.clubcard.authservice.stubs.RefreshTokenRequestStub;
import ru.t1.clubcard.authservice.stubs.RefreshTokenStub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private ClubMemberService clubMemberService;
    @Mock
    private JwtService jwtService;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthenticationService authenticationService;
    private ClubMember clubMember;
    private ClubMemberRegisterRequest clubMemberRegisterRequest;
    private ClubMemberRegisterResponse clubMemberRegisterResponse;
    private ClubMemberLoginRequest clubMemberLoginRequest;
    private RefreshTokenRequest refreshTokenRequest;
    private RefreshToken refreshToken;
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";


    @BeforeEach
    void setUp() {
        clubMember = ClubMemberStub.getClubMEmberStub();
        clubMemberRegisterRequest = ClubMemberRegisterRequestStub.getClubMemberRegisterRequestStub();
        clubMemberRegisterResponse = ClubMemberRegisterResponseStub.getClubMemberRegisterResponseStub();
        clubMemberLoginRequest = ClubMemberLoginRequestStub.getClubMemberLoginRequestStub();
        refreshTokenRequest = RefreshTokenRequestStub.getRefreshTokenRequest();
        refreshToken = RefreshTokenStub.getRefreshTokenStub();
    }

    @Test
    void registerPositiveTest() {
        when(clubMemberService.mapClubMemberRegisterRequest(clubMemberRegisterRequest)).thenReturn(clubMember);
        when(clubMemberService.mapClubMember(clubMember)).thenReturn(clubMemberRegisterResponse);
        when(clubMemberService.create(clubMember)).thenReturn(clubMember);

        assertNotNull(authenticationService.register(clubMemberRegisterRequest));

        verify(clubMemberService, times(1)).mapClubMemberRegisterRequest(clubMemberRegisterRequest);
        verify(clubMemberService, times(1)).mapClubMember(clubMember);
        verify(clubMemberService, times(1)).create(clubMember);
    }

    @Test
    void loginPositiveTest() {
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        when(clubMemberService.userDetailsService()).thenReturn(userDetailsService);
        when(userDetailsService.loadUserByUsername(clubMember.getUsername())).thenReturn(clubMember);
        when(jwtService.generateToken(clubMember)).thenReturn(ACCESS_TOKEN);
        when(refreshTokenService.create(clubMember.getEmail())).thenReturn(REFRESH_TOKEN);

        ClubMemberLoginResponse response = authenticationService.login(clubMemberLoginRequest);

        assertNotNull(response);
        assertEquals(ACCESS_TOKEN, response.getAccessToken());
        assertEquals(REFRESH_TOKEN, response.getRefreshToken());
        verify(clubMemberService, times(1)).userDetailsService();
        verify(userDetailsService, times(1)).loadUserByUsername(clubMember.getUsername());
        verify(jwtService, times(1)).generateToken(clubMember);
        verify(refreshTokenService, times(1)).create(clubMember.getEmail());
    }

    @Test
    void refreshTokenPositiveTest() {
        UserDetailsService userDetailsService = mock(UserDetailsService.class);
        when(clubMemberService.userDetailsService()).thenReturn(userDetailsService);
        when(userDetailsService.loadUserByUsername(refreshToken.getClubMember().getUsername())).thenReturn(clubMember);
        when(refreshTokenService.getByToken(refreshTokenRequest.getToken())).thenReturn(refreshToken);
        when(jwtService.generateToken(clubMember)).thenReturn(ACCESS_TOKEN);
        when(refreshTokenService.create(clubMember.getEmail())).thenReturn(REFRESH_TOKEN);

        ClubMemberLoginResponse response = authenticationService.refreshToken(refreshTokenRequest);

        assertNotNull(response);
        assertEquals(ACCESS_TOKEN, response.getAccessToken());
        assertEquals(REFRESH_TOKEN, response.getRefreshToken());
        verify(clubMemberService, times(1)).userDetailsService();
        verify(userDetailsService, times(1)).loadUserByUsername(refreshToken.getClubMember().getUsername());
        verify(jwtService, times(1)).generateToken(clubMember);
        verify(refreshTokenService, times(1)).create(clubMember.getEmail());
    }

}
