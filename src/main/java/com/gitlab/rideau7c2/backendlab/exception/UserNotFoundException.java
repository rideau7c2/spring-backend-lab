package com.gitlab.rideau7c2.backendlab.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Uzytkownik o id:%d nie istnieje".formatted(id));
    }
}
