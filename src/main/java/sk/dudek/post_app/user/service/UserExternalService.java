package sk.dudek.post_app.user.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.dudek.post_app.core.external_api.ExternalApiService;
import sk.dudek.post_app.core.exception.ResponseException;

/**
 * This service is just for communicating with external API
 */
@Service
public class UserExternalService extends ExternalApiService {
    @Value("${sk.dudek.post_app.user.api}")
    private String userApi;

    public UserExternalService(RestTemplate restTemplate) {
        super(restTemplate);
        super.notFoundMessage = "User was not found";
    }

    /**
     * This method only validate user id
     * @param id
     * @throws ResponseException
     */
    public void validateUserId(Integer id) {
        if (id == null) throw new ResponseException(HttpStatus.BAD_REQUEST, "Invalid user ID");

        super.get(userApi + "/" + id, UserResponse.class)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, "User was not found"));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    protected record UserResponse(Integer id) {}
}
