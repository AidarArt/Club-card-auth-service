package ru.t1.clubcard.authservice.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class ClubMemberRegisterRequest {
    @NotNull
    @Email
    @Size(min = 6, max = 255, message = "Email должен быть в диапазоне от 6 до 255 символов")
    private String email;
    @NotNull
    @Size(min = 8, max = 255, message = "Пароль должен быть в диапазоне от 6 до 255 символов")
    private String password;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Zа-яА-Я\\d-_]{1,255}$",
            message = "Имя может состоять только из русских и английских заглавных и строчных букв, цифр 0-9,"
                    + "a также спецсимволов: тире'-' и нижнее подчеркивание'_'. Размеры в диапазоне от 1 до 255 символов")
    private String firstName;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Zа-яА-Я\\d-_]{1,20}$",
            message = "Фамилия может состоять только из русских и английских заглавных и строчных букв, цифр 0-9,"
                    + "a также спецсимволов: тире'-' и нижнее подчеркивание'_'. Размеры в диапазоне от 1 до 255 символов")
    private String lastName;
    @NotNull
    @Pattern(regexp = "^((\\+7|7|8)+(\\d){10})$",
            message = "Номер телефона может начинаться со знака плюс '+' и цифры 7 или цифры 7 или цифры 8 и содержать до 10 символов")
    private String phone;
}
