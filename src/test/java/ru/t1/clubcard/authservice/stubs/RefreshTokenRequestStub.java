package ru.t1.clubcard.authservice.stubs;

import ru.t1.clubcard.authservice.dto.in.RefreshTokenRequest;

public class RefreshTokenRequestStub {
    private static final String TOKEN = "refreshToken";

    private RefreshTokenRequestStub() {}

    public static RefreshTokenRequest getRefreshTokenRequest() {
        return RefreshTokenRequest.builder()
                .token(TOKEN)
                .build();
    }
}

