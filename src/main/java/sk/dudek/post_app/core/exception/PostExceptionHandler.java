package sk.dudek.post_app.core.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class PostExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ResponseException.class})
    public ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {
        if (ex instanceof ResponseException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        } else {
            return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
        }
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildValidationErrorMessage(ex));
    }

    private String buildValidationErrorMessage(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }
}
