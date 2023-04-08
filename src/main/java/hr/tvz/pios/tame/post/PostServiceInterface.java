package hr.tvz.pios.tame.post;

import java.util.List;
import java.util.Optional;

public interface PostServiceInterface {

    List<PostDTO> findAll();

    List<PostDTO> findAllByMaker(String username);

    Optional<PostDTO> findById(final Long id);

    Optional<PostDTO> save(final PostCommand postCommand);

    void delete(Long id);

    void like(Long postId, String username);

    List<PostDTO> findPostsByFollowedUsers(String username);
}
