package sk.dudek.post_app.post.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.dudek.post_app.core.external_api.ExternalApiService;
import sk.dudek.post_app.core.exception.ResponseException;
import sk.dudek.post_app.post.dto.PostResponse;

/**
 * This service is just for communicating with external API
 */
@Service
public class PostExternalService extends ExternalApiService {
    @Value(value = "${sk.dudek.post_app.post.api}")
    private String postApi;

    public PostExternalService(RestTemplate restTemplate) {
        super(restTemplate);
        super.notFoundMessage = "The post was not found";
    }

    /**
     * This method will try to find a post using external API
     * @param id
     * @return PostResponse
     * @throws ResponseException
     */
    public PostResponse findPostById(Integer id) {
        if (id == null)
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Invalid post ID");

        return super.get(postApi + "/" + id, PostResponse.class)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, "The post was not found"));
    }
}
