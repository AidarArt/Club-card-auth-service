package ru.t1.clubcard.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.clubcard.authservice.model.BlacklistToken;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Long> {
}
