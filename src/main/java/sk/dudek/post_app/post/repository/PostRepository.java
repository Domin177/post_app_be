package sk.dudek.post_app.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.dudek.post_app.post.entity.PostEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    Optional<PostEntity> findFirstByIdOrExternalId(Integer id, Integer externalId);

    List<PostEntity> findAllByUserId(Integer userId);
}
