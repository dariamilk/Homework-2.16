package ru.hogwarts.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FacultyAlreadyExistsException extends RuntimeException {
    public FacultyAlreadyExistsException() {
    }

    public FacultyAlreadyExistsException(String message) {
        super(message);
    }

    public FacultyAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacultyAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public FacultyAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
