package sk.dudek.post_app.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleResponse<T> {
    private final String message;
    private T data;

    public SimpleResponse(String message) {
        this.message = message;
    }

    public SimpleResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
