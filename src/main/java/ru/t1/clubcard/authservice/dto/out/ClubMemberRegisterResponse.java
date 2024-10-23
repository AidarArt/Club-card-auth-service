package ru.t1.clubcard.authservice.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.t1.clubcard.authservice.enums.ClubMemberRole;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ClubMemberRegisterResponse {
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phone;
    private String privilege;
    private Boolean isLocked;
    private ClubMemberRole role;
}
