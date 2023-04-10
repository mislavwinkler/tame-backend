package hr.tvz.pios.tame.post;

import hr.tvz.pios.tame.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService implements PostServiceInterface{

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(this::mapPostToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> findAllByMaker(String username) {
        return postRepository.findAllByMaker(username).stream().map(this::mapPostToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<PostDTO> findById(Long id) {
        return postRepository.findById(id).map(this::mapPostToDTO);
    }

    @Override
    public Optional<PostDTO> save(PostCommand postCommand) {
        return postRepository.save(mapPostCommandToPost(postCommand)).map(this::mapPostToDTO);
    }

    @Override
    public void delete(Long id) {
        postRepository.delete(id);
    }

    @Override
    public void like(Long postId, String username) {
        postRepository.like(postId, username);
    }

    @Override
    public List<PostDTO> findPostsByFollowedUsers(String username) {
        return postRepository.findPostsByFollowedUsers(username).stream().map(this::mapPostToDTO).collect(Collectors.toList());
    }

    private PostDTO mapPostToDTO(final Post post) {
        return new PostDTO(post.getId(), post.getText(), post.getCreationDate(), post.getMaker().getUsername());
    }

    private Post mapPostCommandToPost(PostCommand postCommand) {
        return new Post(postCommand.getText(),
                userRepository.findByUsername(postCommand.getMakerUsername()).orElse(null));
    }
}
