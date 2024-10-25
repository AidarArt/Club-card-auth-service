package ru.t1.clubcard.authservice.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.t1.clubcard.authservice.dto.in.ClubMemberLoginRequest;
import ru.t1.clubcard.authservice.dto.in.ClubMemberRegisterRequest;
import ru.t1.clubcard.authservice.dto.in.RefreshTokenRequest;
import ru.t1.clubcard.authservice.dto.out.ClubMemberLoginResponse;
import ru.t1.clubcard.authservice.integration.configuration.TestcontainersConfiguration;
import ru.t1.clubcard.authservice.mapper.ClubMemberMapper;
import ru.t1.clubcard.authservice.model.ClubMember;
import ru.t1.clubcard.authservice.repository.ClubMemberRepository;
import ru.t1.clubcard.authservice.repository.RefreshTokenRepository;
import ru.t1.clubcard.authservice.service.AuthenticationService;
import ru.t1.clubcard.authservice.stubs.ClubMemberLoginRequestStub;
import ru.t1.clubcard.authservice.stubs.ClubMemberRegisterRequestStub;

import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthenticationControllerTest extends TestcontainersConfiguration {
    private static final String URL_TEMPLATE = "/api/v1/club-card/auth-service";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ClubMemberMapper clubMemberMapper;
    private final ClubMemberRegisterRequest clubMemberRegisterRequest = ClubMemberRegisterRequestStub
            .getClubMemberRegisterRequestStub();
    private final ClubMemberLoginRequest clubMemberLoginRequest = ClubMemberLoginRequestStub
            .getClubMemberLoginRequestStub();
    private RefreshTokenRequest refreshTokenRequest;

    @BeforeEach
    void setUp() {
        clubMemberRepository.deleteAll();
    }

    @Test
    void registerPositiveTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(URL_TEMPLATE.concat("/register"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(clubMemberRegisterRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assert response.contains("\"email\":\"some@email.ru\"");
        assert !response.contains("\"password\":\"password\"");
        assert response.contains("\"firstName\":\"Иван\"");

        ClubMember clubMember = clubMemberRepository.findByEmail(clubMemberRegisterRequest.getEmail()).orElse(null);
        assert clubMember != null;
    }

    @Test
    void registrationNegativeTest_ClubMemberWithEmailAlreadyExist() throws Exception {
        clubMemberRepository.save(clubMemberMapper.mapClubMemberRegisterRequest(clubMemberRegisterRequest));

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(URL_TEMPLATE.concat("/register"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(clubMemberRegisterRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn();

        assert clubMemberRepository.findAll().size() == 1;

        String response = result.getResponse().getContentAsString();
        assert response.contains("\"message\":\"Пользователь с таким Email уже существует\"");
    }

    @Test
    void registrationNegativeTest_ClubMemberWithPhoneAlreadyExist() throws Exception {
        clubMemberRepository.save(clubMemberMapper.mapClubMemberRegisterRequest(clubMemberRegisterRequest));
        clubMemberRegisterRequest.setEmail("some1@email.ru");

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(URL_TEMPLATE.concat("/register"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(clubMemberRegisterRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn();

        assert clubMemberRepository.findAll().size() == 1;

        String response = result.getResponse().getContentAsString();
        assert response.contains("\"message\":\"Пользователь с таким номером телефона уже существует\"");
    }

    @Test
    void loginPositiveTest() throws Exception {
        authenticationService.register(clubMemberRegisterRequest);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(URL_TEMPLATE.concat("/login"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(clubMemberLoginRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        byte[] response = result.getResponse().getContentAsByteArray();
        ClubMemberLoginResponse clubMemberLoginResponse = objectMapper.readValue(response, new TypeReference<>() { });

        assert clubMemberLoginResponse.getAccessToken() != null;
        assert clubMemberLoginResponse.getRefreshToken() != null;
    }

    @Test
    void loginNegativeTest_EmailIsNotCorrect() throws Exception {
        authenticationService.register(clubMemberRegisterRequest);
        clubMemberLoginRequest.setEmail("wrong email");
        mockMvc.perform(
                    MockMvcRequestBuilders
                            .post(URL_TEMPLATE.concat("/login"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(clubMemberLoginRequest))
            )
            .andExpect(MockMvcResultMatchers.status().isForbidden())
            .andReturn();
    }

    @Test
    void loginNegativeTest_PasswordIsNotCorrect() throws Exception {
        authenticationService.register(clubMemberRegisterRequest);
        clubMemberLoginRequest.setPassword("wrong password");
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(URL_TEMPLATE.concat("/login"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(clubMemberLoginRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    void refreshTokenPositiveTest() throws Exception {
        refreshTokenRepository.deleteAll();
        authenticationService.register(clubMemberRegisterRequest);
        ClubMemberLoginResponse loginResponse = authenticationService.login(clubMemberLoginRequest);
        refreshTokenRequest = RefreshTokenRequest.builder().token(loginResponse.getRefreshToken()).build();

        TimeUnit.MILLISECONDS.sleep(1001);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(URL_TEMPLATE.concat("/refresh-token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(refreshTokenRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assert refreshTokenRepository.findAll().size() == 1;

        ClubMemberLoginResponse newLoginResponse = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(), new TypeReference<>(){ });

        assert !loginResponse.getRefreshToken().equals(newLoginResponse.getRefreshToken());
        assert !loginResponse.getAccessToken().equals(newLoginResponse.getAccessToken());
    }

    @Test
    void refreshTokenNegativeTest_AccessTokenExpirationIsExpired() throws Exception {
        refreshTokenRepository.deleteAll();
        authenticationService.register(clubMemberRegisterRequest);
        ClubMemberLoginResponse loginResponse = authenticationService.login(clubMemberLoginRequest);
        refreshTokenRequest = RefreshTokenRequest.builder().token(loginResponse.getRefreshToken()).build();

        TimeUnit.MILLISECONDS.sleep(1501);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(URL_TEMPLATE.concat("/refresh-token"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(refreshTokenRequest))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
