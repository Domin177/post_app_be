package sk.dudek.post_app.core.external_api;

import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import sk.dudek.post_app.core.exception.ResponseException;

import java.util.Optional;

public abstract class ExternalApiService {
    protected String notFoundMessage = "Requested record was not found";
    private final RestTemplate restTemplate;

    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Call Http GET on specific URL with error handling
     * @param url
     * @param clazz
     * @return
     * @param <T>
     */
    protected <T> Optional<T> get(String url, Class<T> clazz) {
        try {
            ResponseEntity<T> result = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, clazz);

            if (result.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(result.getBody());
            } else {
                resolveErrorStatusCode(result.getStatusCode(), "The post was not found");
            }
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode().is5xxServerError()) ex.printStackTrace();

            resolveErrorStatusCode(ex.getStatusCode(), ex.getMessage());
        }

        return Optional.empty();
    }

    protected void resolveErrorStatusCode(HttpStatusCode code, String message) {
        if (code.is5xxServerError()) {
            throw new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR, "Connection error with external API: " + message);
        } else if (code.is4xxClientError()) {
            if (code == HttpStatus.NOT_FOUND) {
                throw new ResponseException(HttpStatus.NOT_FOUND, notFoundMessage);
            } else {
                throw new ResponseException(HttpStatus.resolve(code.value()), message);
            }
        } else {
            throw new ResponseException(HttpStatus.resolve(code.value()), message);
        }
    }
}
