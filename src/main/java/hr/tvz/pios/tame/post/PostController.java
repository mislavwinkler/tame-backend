package hr.tvz.pios.tame.post;

import hr.tvz.pios.tame.security.dto.UserDTO;
import hr.tvz.pios.tame.security.service.AuthenticationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("post")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private final PostService postService;

    private final AuthenticationServiceImpl authenticationService;

    public PostController(PostService postService, AuthenticationServiceImpl authenticationService) {
        this.postService = postService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public List<PostDTO> getAllPost(){
        return postService.findAll();
    }

    @GetMapping("/maker={username}")
    public List<PostDTO> getAllPostByMaker(@PathVariable final String username){
        return postService.findAllByMaker(username);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable final Long id){
        return postService.findById(id).map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }

    @PostMapping
    public ResponseEntity<PostDTO> save(@Valid @RequestBody final PostCommand postCommand){
        return postService.save(postCommand)
                .map(postDTO -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(postDTO))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.CONFLICT)
                                .build()
                );
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id){
        postService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{postId}/{username}")
    public void like (@PathVariable Long postId, @PathVariable String username){
        postService.like(postId, username);
    }

    @GetMapping("/user={username}")
    public List<PostDTO> getAllPostsByFollowedUsers(@PathVariable final String username){
        return postService.findPostsByFollowedUsers(username);
    }

    @GetMapping("/liked/{id}")
    public List<UserDTO> getAllUsersThatLikedPost(@PathVariable Long id){
        return authenticationService.findUserThatLikedPostById(id);
    }
}
