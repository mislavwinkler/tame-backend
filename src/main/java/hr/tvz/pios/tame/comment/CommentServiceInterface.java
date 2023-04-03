package hr.tvz.pios.tame.comment;

import hr.tvz.pios.tame.post.PostCommand;
import hr.tvz.pios.tame.post.PostDTO;

import java.util.List;
import java.util.Optional;

public interface CommentServiceInterface {

    List<CommentDTO> findAll();

    Optional<CommentDTO> findById(Long id);

    List<CommentDTO> findCommentsByPostId(final Long postId);

    Optional<CommentDTO> save(final CommentCommand commentCommand);

    void delete(Long id);
}
