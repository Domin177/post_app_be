package sk.dudek.post_app.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PostResponse {
    private Integer id;
    private Integer externalId;
    private Integer userId;
    private String title;
    private String body;
}
