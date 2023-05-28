package sk.dudek.post_app.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sk.dudek.post_app.core.exception.ResponseException;
import sk.dudek.post_app.post.dto.PostEditRequest;
import sk.dudek.post_app.post.dto.PostRequest;
import sk.dudek.post_app.post.dto.PostResponse;
import sk.dudek.post_app.post.entity.PostEntity;
import sk.dudek.post_app.post.mapper.PostMapper;
import sk.dudek.post_app.post.repository.PostRepository;
import sk.dudek.post_app.user.service.UserExternalService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserExternalService userExternalService;
    private final PostMapper postMapper;
    private final PostExternalService postExternalService;

    public PostResponse addPost(PostRequest postRequest) {
        userExternalService.validateUserId(postRequest.getUserId());

        PostEntity entity = postRepository.save(postMapper.toPostEntity(postRequest));
        return postMapper.toPostResponse(entity);
    }

    /**
     * This method will try to find post in our database by ID or External ID,
     * if not exists in our database then it is going to try find it in external API and if its found it will be created and returned
     * otherwise it will throw ResponseException which is handled by PostExceptionHandler
     * @param id
     * @return PostResponse
     */
    public PostResponse getPostById(Integer id) {
        return postRepository.findFirstByIdOrExternalId(id, id)
                .map(postMapper::toPostResponse)
                .orElseGet(() -> findInExternalApi(id));
    }

    public List<PostResponse> getPostsByUserId(Integer userId) {
        userExternalService.validateUserId(userId);

        return postRepository.findAllByUserId(userId)
                .stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
    }

    public void deletePost(Integer id) {
        if (!postRepository.existsById(id))
            throw new ResponseException(HttpStatus.NOT_FOUND, "The post was not found");

        postRepository.deleteById(id);
    }

    public PostResponse updatePost(Integer postId, PostEditRequest postRequest) {
        PostEntity entity = getPostEntity(postId);

        postRepository.save(postMapper.updatePostEntity(postRequest, entity));

        return postMapper.toPostResponse(entity);
    }

    private PostResponse findInExternalApi(Integer id) {
        PostResponse response = postExternalService.findPostById(id);
        PostEntity entity = postRepository.save(postMapper.toPostEntity(response)
                        .setExternalId(response.getId()));

        return postMapper.toPostResponse(entity);
    }
    private PostEntity getPostEntity(Integer id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseException(HttpStatus.NOT_FOUND, "The post was not found"));
    }
}
