package ru.t1.clubcard.authservice.dto.in;

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
public class RefreshTokenRequest {
    @NotNull
    @Size(min = 36, max = 36)
    private String token;
}
