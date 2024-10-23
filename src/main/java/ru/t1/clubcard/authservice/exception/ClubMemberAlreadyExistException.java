package ru.t1.clubcard.authservice.exception;

public class ClubMemberAlreadyExistException extends RuntimeException {
    public ClubMemberAlreadyExistException(String message) {
        super(message);
    }
}
