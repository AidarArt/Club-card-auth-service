package ru.t1.clubcard.authservice.stubs;

import ru.t1.clubcard.authservice.model.ClubMember;
import ru.t1.clubcard.authservice.model.RefreshToken;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class RefreshTokenStub {
    private static final long ID = 1L;
    private static final String TOKEN = "5939870b-a7ef-4e9e-9270-2adfa1b2dd96";
    private static final LocalDateTime EXPIRY_DATE = Instant.ofEpochMilli(System.currentTimeMillis() + 3600000)
                                                                .atZone(ZoneId.systemDefault()).toLocalDateTime();
    private static final ClubMember CLUB_MEMBER = ClubMemberStub.getClubMEmberStub();

    private RefreshTokenStub() {}

    public static RefreshToken getRefreshTokenStub() {
        return RefreshToken.builder()
                .id(ID)
                .token(TOKEN)
                .expiryDate(EXPIRY_DATE)
                .clubMember(CLUB_MEMBER)
                .build();
    }
}
