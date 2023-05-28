package sk.dudek.post_app.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.dudek.post_app.core.response.SimpleResponse;
import sk.dudek.post_app.post.dto.PostEditRequest;
import sk.dudek.post_app.post.dto.PostRequest;
import sk.dudek.post_app.post.service.PostService;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;

    @Operation(summary = "Create new post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The post has been successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<?> addPost(@Valid @RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(new SimpleResponse<>(
                "The post has been successfully created",
                postService.addPost(postRequest)
        ));
    }

    @Operation(summary = "Update post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The post has been successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "The post was not found"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Integer id, @Valid @RequestBody PostEditRequest postRequest) {
        return ResponseEntity.ok(new SimpleResponse<>(
                "The post has been successfully updated",
                postService.updatePost(id, postRequest)
        ));
    }

    @Operation(summary = "Get post by ID. If not found it will try to find post using external API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The post was found"),
            @ApiResponse(responseCode = "404", description = "The post was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(summary = "Get a post by user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The post was found"),
            @ApiResponse(responseCode = "404", description = "The post was not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPostsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    @Operation(summary = "Delete post by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The post has been successfully deleted"),
            @ApiResponse(responseCode = "404", description = "The post was not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.ok(new SimpleResponse<>("The post has been successfully removed"));
    }
}
