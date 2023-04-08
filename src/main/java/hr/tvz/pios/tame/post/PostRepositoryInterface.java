package hr.tvz.pios.tame.post;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryInterface {

    List<Post> findAll();

    List<Post> findAllByMaker(String makerUsername);

    Optional<Post> findById(Long id);

    Optional<Post> save(Post Post);

    void delete(Long id);

    void like(Long postId, String username);

    List<Post> findPostsByFollowedUsers(String username);

}
