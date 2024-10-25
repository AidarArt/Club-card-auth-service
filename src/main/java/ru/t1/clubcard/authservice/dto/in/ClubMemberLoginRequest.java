package ru.t1.clubcard.authservice.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 6, max = 255, message = "Email должен быть в диапазоне от 6 до 255 символов")
    private String email;
    @NotNull
    @Size(min = 8, max = 255, message = "Пароль должен быть в диапазоне от 6 до 255 символов")
    private String password;
}
