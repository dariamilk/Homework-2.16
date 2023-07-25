package ru.hogwarts.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchFacultyException extends RuntimeException{
    public NoSuchFacultyException() {
    }

    public NoSuchFacultyException(String message) {
        super(message);
    }

    public NoSuchFacultyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchFacultyException(Throwable cause) {
        super(cause);
    }

    public NoSuchFacultyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
