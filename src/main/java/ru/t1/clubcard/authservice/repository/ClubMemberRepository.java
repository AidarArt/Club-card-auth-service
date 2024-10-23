package ru.t1.clubcard.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.clubcard.authservice.model.ClubMember;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Optional<ClubMember> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
