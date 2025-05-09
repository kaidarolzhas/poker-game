package kz.kaidarolzhas.pokerbackend.data.exception;

public class NotFoundInDatabaseException extends RuntimeException {
    public NotFoundInDatabaseException(String message) {
        super(message);
    }
}
