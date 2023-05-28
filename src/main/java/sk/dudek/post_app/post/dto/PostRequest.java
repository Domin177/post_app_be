package sk.dudek.post_app.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    @NotNull(message = "User id cannot be null")
    private Integer userId;
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 255)
    private String title;
    @NotBlank(message = "Body cannot be empty")
    @Size(max = 500)
    private String body;
}
