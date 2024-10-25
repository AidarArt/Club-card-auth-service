package ru.t1.clubcard.authservice.stubs;

import ru.t1.clubcard.authservice.dto.in.ClubMemberRegisterRequest;

public class ClubMemberRegisterRequestStub {
    private static final String EMAIL = "some@email.ru";
    private static final String PASSWORD = "password";
    private static final String FIRSTNAME = "Иван";
    private static final String LASTNAME = "Иванов";
    private static final String PHONE = "+79999999999";

    private ClubMemberRegisterRequestStub() {}

    public static ClubMemberRegisterRequest getClubMemberRegisterRequestStub() {
        return ClubMemberRegisterRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .phone(PHONE)
                .build();
    }
}
