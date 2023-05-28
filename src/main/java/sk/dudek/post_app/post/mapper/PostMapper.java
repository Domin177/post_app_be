package sk.dudek.post_app.post.mapper;

import org.springframework.stereotype.Component;
import sk.dudek.post_app.post.dto.PostEditRequest;
import sk.dudek.post_app.post.dto.PostRequest;
import sk.dudek.post_app.post.dto.PostResponse;
import sk.dudek.post_app.post.entity.PostEntity;

/**
 * For bigger projects is better to use some mapping library like MapStruct
 */
@Component
public class PostMapper {
    public PostResponse toPostResponse(PostEntity entity) {
        return new PostResponse()
                .setTitle(entity.getTitle())
                .setBody(entity.getBody())
                .setUserId(entity.getUserId())
                .setId(entity.getId())
                .setExternalId(entity.getExternalId());
    }

    public PostEntity toPostEntity(PostRequest postRequest) {
        return new PostEntity()
                .setTitle(postRequest.getTitle())
                .setBody(postRequest.getBody())
                .setUserId(postRequest.getUserId());
    }

    public PostEntity toPostEntity(PostResponse postResponse) {
        return new PostEntity()
                .setTitle(postResponse.getTitle())
                .setBody(postResponse.getBody())
                .setUserId(postResponse.getUserId());
    }

    public PostEntity updatePostEntity(PostEditRequest postRequest, PostEntity entity) {
        entity.setBody(postRequest.getBody());
        entity.setTitle(postRequest.getTitle());

        return entity;
    }
}
