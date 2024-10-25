package ru.t1.clubcard.authservice.module;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.clubcard.authservice.model.BlacklistToken;
import ru.t1.clubcard.authservice.repository.BlacklistTokenRepository;
import ru.t1.clubcard.authservice.service.BlacklistTokenService;
import ru.t1.clubcard.authservice.stubs.BlacklistTokenStub;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlacklistTokenServiceTest {
    @Mock
    private BlacklistTokenRepository blacklistTokenRepository;
    @InjectMocks
    private BlacklistTokenService blacklistTokenService;
    private BlacklistToken blacklistToken;

    @BeforeEach
    void setUp() {
        blacklistToken = BlacklistTokenStub.getBlacklistTokenStub();
    }

    @Test
    void createPositiveTest() {
        when(blacklistTokenRepository.save(any(BlacklistToken.class))).thenReturn(blacklistToken);

        assertNotNull(blacklistTokenService.create(blacklistToken.getToken()));
        verify(blacklistTokenRepository, times(1)).save(any(BlacklistToken.class));
    }

}
