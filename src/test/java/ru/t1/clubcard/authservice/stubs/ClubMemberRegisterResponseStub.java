package ru.t1.clubcard.authservice.stubs;

import ru.t1.clubcard.authservice.dto.out.ClubMemberRegisterResponse;
import ru.t1.clubcard.authservice.enums.ClubMemberRole;

import java.time.LocalDate;

public class ClubMemberRegisterResponseStub {
    private static final long ID = 1L;
    private static final String EMAIL = "some@email.ru";
    private static final String PASSWORD = "password";
    private static final String FIRSTNAME = "Иван";
    private static final String LASTNAME = "Иванов";
    private static final LocalDate BIRTHDAY = LocalDate.of(1999, 1, 1);
    private static final String PHONE = "+79999999999";
    private static final String PRIVILEGE = "basic";
    private static final Boolean IS_LOCKED = false;
    private static final ClubMemberRole ROLE = ClubMemberRole.ROLE_USER;

    private ClubMemberRegisterResponseStub() {}

    public static ClubMemberRegisterResponse getClubMemberRegisterResponseStub() {
        return ClubMemberRegisterResponse.builder()
                .id(ID)
                .email(EMAIL)
                .password(PASSWORD)
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .birthday(BIRTHDAY)
                .phone(PHONE)
                .privilege(PRIVILEGE)
                .isLocked(IS_LOCKED)
                .role(ROLE)
                .build();
    }
}
