package ru.t1.clubcard.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.t1.clubcard.authservice.dto.in.ClubMemberRegisterRequest;
import ru.t1.clubcard.authservice.dto.out.ClubMemberRegisterResponse;
import ru.t1.clubcard.authservice.exception.ClubMemberAlreadyExistException;
import ru.t1.clubcard.authservice.mapper.ClubMemberMapper;
import ru.t1.clubcard.authservice.model.ClubMember;
import ru.t1.clubcard.authservice.repository.ClubMemberRepository;

@Service
@RequiredArgsConstructor
public class ClubMemberService {
    private final ClubMemberRepository clubMemberRepository;
    private final ClubMemberMapper clubMemberMapper;

    public ClubMember save(ClubMember clubMember) {
        return clubMemberRepository.save(clubMember);
    }

    public ClubMember create(ClubMember clubMember) {
        if (clubMemberRepository.existsByEmail(clubMember.getEmail())) {
            throw new ClubMemberAlreadyExistException("Пользователь с таким Email уже существует");
        }
        if (clubMemberRepository.existsByPhone(clubMember.getPhone())) {
            throw new ClubMemberAlreadyExistException("Пользователь с таким номером телефона уже существует");
        }
        return save(clubMember);
    }

    public ClubMember getByUsername(String email) {
        return clubMemberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользоватль не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public ClubMember getCurrentClubMember() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public ClubMember mapClubMemberRegisterRequest(ClubMemberRegisterRequest request) {
        return clubMemberMapper.mapClubMemberRegisterRequest(request);
    }

    public ClubMemberRegisterResponse mapClubMember(ClubMember clubMember) {
        return clubMemberMapper.mapClubMember(clubMember);
    }


}
