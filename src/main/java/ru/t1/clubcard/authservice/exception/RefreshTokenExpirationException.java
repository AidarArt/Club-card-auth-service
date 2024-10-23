package ru.t1.clubcard.authservice.exception;

public class RefreshTokenExpirationException extends RuntimeException {
    public RefreshTokenExpirationException() {
        super("Время жизни токена обновления истекло");
    }
}
