package ru.t1.clubcard.authservice.module;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.t1.clubcard.authservice.exception.ClubMemberAlreadyExistException;
import ru.t1.clubcard.authservice.mapper.ClubMemberMapper;
import ru.t1.clubcard.authservice.model.ClubMember;
import ru.t1.clubcard.authservice.repository.ClubMemberRepository;
import ru.t1.clubcard.authservice.service.ClubMemberService;
import ru.t1.clubcard.authservice.stubs.ClubMemberStub;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClubMemberServiceTest {
    @Mock
    private ClubMemberRepository clubMemberRepository;
    @Mock
    private ClubMemberMapper clubMemberMapper;
    @InjectMocks
    private ClubMemberService clubMemberService;
    private ClubMember clubMember;

    @BeforeEach
    public void setUp() {
        clubMember = ClubMemberStub.getClubMEmberStub();

    }

    @Test
    void getByUsernamePositiveTest() {
        when(clubMemberRepository.findByEmail(clubMember.getEmail())).thenReturn(Optional.of(clubMember));

        assertNotNull(clubMemberService.getByUsername(clubMember.getEmail()));
        verify(clubMemberRepository, times(1)).findByEmail(clubMember.getEmail());
    }

    @Test
    void getByUsernameThrowsExceptionTest() {
        when(clubMemberRepository.findByEmail(clubMember.getEmail())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(UsernameNotFoundException.class,
                () -> clubMemberService.getByUsername(clubMember.getEmail()));
        assertNotNull(exception);
        verify(clubMemberRepository, times(1)).findByEmail(clubMember.getEmail());
    }

    @Test
    void createPositiveTest() {
        when(clubMemberRepository.existsByEmail(clubMember.getEmail())).thenReturn(false);
        when(clubMemberRepository.existsByPhone(clubMember.getPhone())).thenReturn(false);
        when(clubMemberRepository.save(clubMember)).thenReturn(clubMember);

        assertNotNull(clubMemberService.create(clubMember));
        verify(clubMemberRepository, times(1)).existsByPhone(clubMember.getPhone());
        verify(clubMemberRepository, times(1)).existsByEmail(clubMember.getEmail());
        verify(clubMemberRepository, times(1)).save(clubMember);

    }

    @Test
    void createThrowsExceptionIfEmailNotUniqueTest() {
        when(clubMemberRepository.existsByEmail(clubMember.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(ClubMemberAlreadyExistException.class,
                () -> clubMemberService.create(clubMember));
        assertNotNull(exception);
        verify(clubMemberRepository, times(1)).existsByEmail(clubMember.getEmail());

    }

    @Test
    void createThrowsExceptionIfPhoneNotUniqueTest() {
        when(clubMemberRepository.existsByEmail(clubMember.getEmail())).thenReturn(false);
        when(clubMemberRepository.existsByPhone(clubMember.getPhone())).thenReturn(true);

        RuntimeException exception = assertThrows(ClubMemberAlreadyExistException.class,
                () -> clubMemberService.create(clubMember));
        assertNotNull(exception);
        verify(clubMemberRepository, times(1)).existsByPhone(clubMember.getPhone());
        verify(clubMemberRepository, times(1)).existsByEmail(clubMember.getEmail());
    }

    @Test
    void getCurrentClubMemberPositiveTest() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(clubMember.getEmail());
        when(clubMemberRepository.findByEmail(clubMember.getEmail())).thenReturn(Optional.of(clubMember));

        assertNotNull(clubMemberService.getCurrentClubMember());

        verify(securityContext, times(1)).getAuthentication();
        verify(authentication, times(1)).getName();
        verify(clubMemberRepository, times(1)).findByEmail(clubMember.getEmail());
    }
}
