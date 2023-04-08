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
    //@Secured({"ROLE_ADMIN"})
    public List<PostDTO> getAllPost(){
        return postService.findAll();
    }

    @GetMapping("/maker={username}")
    //@Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<PostDTO> getAllPostByMaker(@PathVariable final String username){
        return postService.findAllByMaker(username);
    }

    @GetMapping("/{id}")
    //@Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<PostDTO> getPostById(@PathVariable final Long id){
        return postService.findById(id).map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }

    @PostMapping
    //@Secured({"ROLE_USER", "ROLE_ADMIN"})
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
    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    public void delete (@PathVariable Long id){
        postService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{postId}/{username}")
    //@Secured({"ROLE_USER","ROLE_ADMIN"})
    public void like (@PathVariable Long postId, @PathVariable String username){
        postService.like(postId, username);
    }

    @GetMapping("/user={username}")
    //@Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<PostDTO> getAllPostsByFollowedUsers(@PathVariable final String username){
        return postService.findPostsByFollowedUsers(username);
    }

    @GetMapping("/liked/{id}")
    //@Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<UserDTO> getAllUsersThatLikedPost(@PathVariable Long id){
        return authenticationService.findUserThatLikedPostById(id);
    }
}
