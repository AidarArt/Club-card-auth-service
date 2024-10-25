package ru.t1.clubcard.authservice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.t1.clubcard.authservice.exception.ClubMemberAlreadyExistException;
import ru.t1.clubcard.authservice.exception.ErrorMessage;
import ru.t1.clubcard.authservice.exception.RefreshTokenExpirationException;
import ru.t1.clubcard.authservice.exception.TokenNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorMessage> handle(Exception ex, HttpStatus httpStatus) {
        return new ResponseEntity<>(
                new ErrorMessage(
                        ex.getMessage()
                ), httpStatus
        );
    }

    @ExceptionHandler(ClubMemberAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> handleClubMemberAlreadyExistException(ClubMemberAlreadyExistException ex) {
        return handle(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RefreshTokenExpirationException.class)
    public ResponseEntity<ErrorMessage> handleRefreshTokenExpirationException(RefreshTokenExpirationException ex) {
        return handle(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleTokenNotFoundException(TokenNotFoundException ex) {
        return handle(ex, HttpStatus.NOT_FOUND);
    }
}
