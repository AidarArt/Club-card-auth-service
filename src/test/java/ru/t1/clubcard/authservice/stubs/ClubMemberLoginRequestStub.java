package ru.t1.clubcard.authservice.stubs;

import ru.t1.clubcard.authservice.dto.in.ClubMemberLoginRequest;

public class ClubMemberLoginRequestStub {
    private static final String EMAIL = "some@email.ru";
    private static final String PASSWORD = "password";

    private ClubMemberLoginRequestStub() {}

    public static ClubMemberLoginRequest getClubMemberLoginRequestStub() {
        return ClubMemberLoginRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }
}
