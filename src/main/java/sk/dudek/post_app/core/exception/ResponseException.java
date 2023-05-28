package sk.dudek.post_app.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ResponseException extends RuntimeException {
    @Getter
    private final HttpStatus httpStatus;

    public ResponseException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
