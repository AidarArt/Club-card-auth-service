package ru.t1.clubcard.authservice.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClubMemberLoginResponse {
    private String accessToken;
    private String refreshToken;
}
