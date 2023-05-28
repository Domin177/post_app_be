
package sk.dudek.post_app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class PostAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostAppApplication.class, args);
    }

}
