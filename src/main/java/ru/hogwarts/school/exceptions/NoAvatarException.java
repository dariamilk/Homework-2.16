package ru.hogwarts.school.exceptions;

public class NoAvatarException extends RuntimeException {
    public NoAvatarException() {
    }

    public NoAvatarException(String message) {
        super(message);
    }

    public NoAvatarException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvatarException(Throwable cause) {
        super(cause);
    }

    public NoAvatarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
