package hr.tvz.pios.tame.comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryInterface {

    List<Comment> findAll();

    Optional<Comment> findById(Long id);

    List<Comment> findCommentsByPostId(Long postId);

    Optional<Comment> save(Comment answer);

    void delete(Long id);
}
