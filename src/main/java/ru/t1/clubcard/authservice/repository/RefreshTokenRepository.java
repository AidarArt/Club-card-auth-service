package ru.t1.clubcard.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.clubcard.authservice.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
