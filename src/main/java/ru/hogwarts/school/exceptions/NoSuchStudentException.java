package ru.hogwarts.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchStudentException extends RuntimeException{
    public NoSuchStudentException() {
    }

    public NoSuchStudentException(String message) {
        super(message);
    }

    public NoSuchStudentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchStudentException(Throwable cause) {
        super(cause);
    }

    public NoSuchStudentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
