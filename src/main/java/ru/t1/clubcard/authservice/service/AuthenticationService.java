package ru.t1.clubcard.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.clubcard.authservice.dto.in.ClubMemberLoginRequest;
import ru.t1.clubcard.authservice.dto.in.ClubMemberRegisterRequest;
import ru.t1.clubcard.authservice.dto.in.RefreshTokenRequest;
import ru.t1.clubcard.authservice.dto.out.ClubMemberLoginResponse;
import ru.t1.clubcard.authservice.dto.out.ClubMemberRegisterResponse;
import ru.t1.clubcard.authservice.model.ClubMember;
import ru.t1.clubcard.authservice.model.RefreshToken;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ClubMemberService clubMemberService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;


    public ClubMemberRegisterResponse register(ClubMemberRegisterRequest request) {
        request.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        ClubMember clubMember = clubMemberService.mapClubMemberRegisterRequest(request);

        return clubMemberService.mapClubMember(
                clubMemberService.create(clubMember)
        );
    }

    @Transactional
    public ClubMemberLoginResponse login(ClubMemberLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));

        UserDetails clubMember = clubMemberService.userDetailsService().loadUserByUsername(request.getEmail());
        String accessToken = jwtService.generateToken(clubMember);
        String refreshToken = refreshTokenService.create(clubMember.getUsername());
        return new ClubMemberLoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public ClubMemberLoginResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken token = refreshTokenService.getByToken(request.getToken());

        refreshTokenService.verifyExpiration(token);

        UserDetails clubMember = clubMemberService
                .userDetailsService()
                .loadUserByUsername(token.getClubMember().getEmail());

        refreshTokenService.delete(token);

        String accessToken = jwtService.generateToken(clubMember);
        String refreshToken = refreshTokenService.create(clubMember.getUsername());

        return new ClubMemberLoginResponse(accessToken, refreshToken);
    }
}
