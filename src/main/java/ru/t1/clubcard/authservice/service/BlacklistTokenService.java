package ru.t1.clubcard.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1.clubcard.authservice.model.BlacklistToken;
import ru.t1.clubcard.authservice.repository.BlacklistTokenRepository;

@Service
@RequiredArgsConstructor
public class BlacklistTokenService {
    private final BlacklistTokenRepository blacklistTokenRepository;

    public BlacklistToken save(BlacklistToken blacklistToken) {
       return blacklistTokenRepository.save(blacklistToken);
    }

    public BlacklistToken create(String token) {
        return save(BlacklistToken.builder().token(token).build());
    }
}
