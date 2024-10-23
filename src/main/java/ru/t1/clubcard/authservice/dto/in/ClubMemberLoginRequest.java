package ru.t1.clubcard.authservice.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ClubMemberLoginRequest {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}
