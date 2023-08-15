package ru.practicum.shareit.exception;

public class UserDuplicateEmailException extends RuntimeException {

    public UserDuplicateEmailException() {
        super("User with this email already exists.");
    }

    public UserDuplicateEmailException(String message) {
        super(message);
    }

    public UserDuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}