package hr.tvz.pios.tame.comment;

import hr.tvz.pios.tame.post.PostRepository;
import hr.tvz.pios.tame.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService implements CommentServiceInterface{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream().map(this::mapCommentToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CommentDTO> findById(Long id) {
        return commentRepository.findById(id).map(this::mapCommentToDTO);
    }

    @Override
    public List<CommentDTO> findCommentsByPostId(Long postId) {
        return commentRepository.findCommentsByPostId(postId).stream().map(this::mapCommentToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CommentDTO> save(CommentCommand commentCommand) {
        return commentRepository.save(mapCommentCommandToComment(commentCommand))
                .map(this::mapCommentToDTO);
    }

    @Override
    public void delete(Long id)  {
        commentRepository.delete(id);
    }

    private CommentDTO mapCommentToDTO(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getText(), comment.getPost().getId(), comment.getMaker().getUsername());
    }

    private Comment mapCommentCommandToComment(CommentCommand commentCommand) {
        return new Comment(commentCommand.getId(), commentCommand.getText(),
                userRepository.findByUsername(commentCommand.getMakerUsername()).get(),
                postRepository.findById(commentCommand.getPostId()).get(),
                commentCommand.getCreationDate());
    }
}
