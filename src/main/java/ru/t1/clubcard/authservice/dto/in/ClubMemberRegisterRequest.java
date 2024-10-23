package ru.t1.clubcard.authservice.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class ClubMemberRegisterRequest {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Zа-яА-Я\\d-_]{1,20}$")
    private String firstName;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Zа-яА-Я\\d-_]{1,20}$")
    private String lastName;
    @NotNull
    @Pattern(regexp = "^((\\+7|7|8)+(\\d){10})$")
    private String phone;
}
