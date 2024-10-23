package ru.t1.clubcard.authservice.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Токен не найден или не существует");
    }
}
